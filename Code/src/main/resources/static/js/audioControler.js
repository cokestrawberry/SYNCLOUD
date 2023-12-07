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

    list_audio.forEach((elem, index) => {
        list_audio_play_id.push(elem.play());
        elem.pause(list_audio_play_id[index]);
    });
    setTimeout(function(){
        list_audio.forEach((elem, index) => {
            elem.play();
        });
    }, (60/bpm)*2.5*1000);
}

function audio_stop(){
    Howler.stop();
    while(list_audio_play_id.length>0){
        list_audio_play_id.pop();
    }
}

let recorder = null;
function audio_record_start(BPM, list_audio){
    // recording start
    console.log('recording start');

    navigator.mediaDevices.getUserMedia({ audio: true })
        .then((stream) => {
            // 마이크 스트림을 사용하는 코드 작성

            // list_audio play
            audio_play(BPM, list_audio);
            // recording start
            recorder = new MediaRecorder(stream);
            recorder.start();
        })
        .catch((error) => {
            // 접근 권한이 거부되었거나 오류가 발생한 경우 처리하는 코드 작성
            alert("마이크 권한이 없어 녹음을 할 수 없습니다.")
        });
}
function audio_record_stop(){
    // recording stop
    // add current workspace track
    console.log('recording stop');

    // list_audio stop
    audio_stop();
    // recording stop
    recorder.onstop = () => {
        // 녹음된 데이터를 파일에 저장합니다.
        const blob = recorder.getBlob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = "file:///" + "/Users/mepistos/github/git_Capstone_Design_1/SoundCloud/Code/src/main/resources/static/sample_music/recording.wav";
        a.download = "recording.wav";
        a.click();
    };

    // cur_info_cookie setting
    let info = info_gathering();
    document.cookie = "cur_info_cookie="+info.join(',');
}