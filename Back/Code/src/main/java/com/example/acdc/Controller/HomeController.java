package com.example.acdc.Controller;

import com.example.acdc.domain.*;
import com.example.acdc.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final DownloadService downloadService;
    private final SelectSoundtrackService selectSoundtrackService;

    @GetMapping(value = "/index")
    public String home(Model model) {

        User testUser = userService.findOne(1L);

        Download closedDownload = downloadService.getClosedOne(testUser);
        List<SelectSoundtrack> soundtracks= selectSoundtrackService.findByDownload(closedDownload);
        List<SelectSoundtrack> soundtrackList = soundtracks.stream()
                                  .filter(s -> s.getDeleted().equals(false))
                                  .collect(Collectors.toList());

        model.addAttribute("selectSoundtracks", soundtrackList);
        model.addAttribute("userId", testUser.getId());

        return "index";
    }

    @PostMapping(value = "/index/{selectSoundtrackId}/delete")
    public String deleteSelectSoundtrack(@PathVariable("selectSoundtrackId") Long selectSoundtrackID) {

        SelectSoundtrack S = selectSoundtrackService.findOne(selectSoundtrackID);
        selectSoundtrackService.delete(S);
        selectSoundtrackService.save(S);

        Download download = S.getDownload();
        download.updateModifyDate();
        downloadService.save(download);

        return "redirect:/index";
    }
}

