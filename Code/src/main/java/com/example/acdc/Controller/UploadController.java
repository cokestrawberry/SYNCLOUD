package com.example.acdc.Controller;

import com.example.acdc.domain.*;
import com.example.acdc.service.SongService;
import com.example.acdc.service.SoundtrackService;
import com.example.acdc.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class UploadController {

    private final UserService userService;
    private final SongService songService;
    private final SoundtrackService soundtrackService;

    @GetMapping(value = "/upload/{userId}")
    public String upload(@PathVariable("userId") Long userId, Model model) {

        model.addAttribute("form", new SoundtrackForm());
        model.addAttribute("userId", userId);

        return "upload";
    }

    @PostMapping(value = "/upload/{userId}")
    public String uploadSoundtrack(@PathVariable("userId") Long userId, SoundtrackForm form, HttpServletResponse response) throws Exception {

        User user = userService.findOne(userId);

        if(form.getTitle().isEmpty()) {
            ScriptUnit.alert(response, "제목을 작성해주세요.");
            return "redirect:/upload/" + userId;
        }
        if(form.getArtist().isEmpty()) {
            ScriptUnit.alert(response, "가수 이름을 작성해주세요.");
            return "redirect:/upload/" + userId;
        }
        if(form.getBpm() == null) {
            form.setBpm(0);
        }

        Song song = songService.findByTitleAndArtist(form.getTitle(), form.getArtist());

        if(song == null) {
            /*ScriptUnit.alert(response, "등록되지 않은 곡입니다.");
            return "redirect:/upload/" + userId;*/
            Song newSong = Song.createSong(form.getTitle(), form.getArtist(), form.getBpm());
            songService.save(newSong);

            String path = "sample_music/" + form.getPath();
            Soundtrack soundtrack = Soundtrack.createSoundtrack(user, newSong, form.getSession(), form.getBpm(), form.getNote(), path);
            soundtrackService.save(soundtrack);

        } else {
            String path = "sample_music/" + form.getPath();
            Soundtrack soundtrack = Soundtrack.createSoundtrack(user, song, form.getSession(), form.getBpm(), form.getNote(), path);
            soundtrackService.save(soundtrack);
        }

        ScriptUnit.alert_clear(response, "/upload/" + userId ,"업로드가 완료되었습니다.");

        return "redirect:/upload/"+userId;
    }
}
