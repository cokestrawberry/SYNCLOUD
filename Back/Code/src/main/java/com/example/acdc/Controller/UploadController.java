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
        Song song = songService.findByTitleAndArtist(form.getTitle(), form.getArtist());

        if(song == null) {
            ScriptUnit.alert(response, "등록되지 않은 곡입니다.");
            return "redirect:/upload/" + userId;
        }

        Soundtrack soundtrack = Soundtrack.createSoundtrack(user, song, form.getSession(), form.getBpm(), form.getNote(), "https://test.com");
        soundtrackService.save(soundtrack);

        return "redirect:/upload/"+userId;
    }
}
