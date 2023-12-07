// audio object functions
    // audio_load
function audio_load(btn, audio_obj){
    audio_obj.once('load', function() {
        btn.style.width = '120px';
        btn.innerHTML = 'load done';
        btn.style.display = 'none';
    });

    return audio_obj;
}
    // audio object create
function audio_obj_create(btn, path){
    let audio_obj = new Howl({
        src:[path]
    });

    audio_obj.volume(0.5);

    audio_load(btn, audio_obj);
    return audio_obj;
}
    // audio volume setting
function audio_volume(volume, audio_obj, audio_obj_id){
    audio_obj.volume(volume/100, audio_obj_id);
}

// metronome
let metronome = new Howl({
    src:['sample_music/metronome_100bpm.mp3']
});
    // metronome bpm setting and play
function set_metronome(bpm){
    metronome._rate =  bpm / 100;
}

// audio play function
let list_audio_play_id = new Array();
function audio_play(bpm, list_audio){
    let temp = metronome.play();

    let timeout = 0;
    if (bpm>100)    timeout = (60/bpm)*2.5*1000;
    if (bpm<=100)   timeout = (60/bpm)*4*1000;
    list_audio.forEach((elem, index) => {
        list_audio_play_id.push(elem.play());
        elem.pause(list_audio_play_id[index]);
    });
    setTimeout(function(){
        list_audio.forEach((elem, index) => {
            elem.play();
        });
    }, timeout);
}

function audio_stop(){
    Howler.stop();
    while(list_audio_play_id.length>0){
        list_audio_play_id.pop();
    }
}

let recorder;
function audio_record_start(BPM, list_audio){
    console.log('recording start');

    navigator.mediaDevices.getUserMedia({audio: true}).then((stream)=>{
        recorder = new MediaRecorder(stream, {mimeType:'audio/webm'});
        recorder.ondataavailable = async (event) => {
            const data = event.data;
            const blob = new Blob([data], {type: 'audio/webm'});

            let file_name = 'src_'+record_index+'.webm';
            saveAs(blob, file_name);
            record_index += 1;
        };

        audio_play(BPM, list_audio);

        let timeout = 0;
        if (BPM>100)    timeout = (60/BPM)*2.5*1000;
        if (BPM<=100)   timeout = (60/BPM)*4*1000;
        setTimeout(function(){
            recorder.start();
        }, timeout);
    });
}

function audio_record_stop(){
    // list_audio stop
    audio_stop();
    // recording stop
    recorder.stop();

    console.log('recording stop');

    // cur_info_cookie setting
    let info = info_gathering();
    document.cookie = "cur_info_cookie="+info.join(',');
}