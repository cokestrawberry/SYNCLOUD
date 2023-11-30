package com.example.acdc.Controller;

import com.example.acdc.service.SoundtrackService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class NoteController {

    private final SoundtrackService soundtrackService;

    @GetMapping(value = "/note/{soundtrackId}")
    public String note(@PathVariable("soundtrackId") Long id, Model model) {

        String note = soundtrackService.findOne(id).getNote();
        int bpm = soundtrackService.findOne(id).getBpm();

        model.addAttribute("note", note);
        model.addAttribute("bpm", bpm);

        return "note";
    }


}
