package com.example.acdc.Controller;

import com.example.acdc.domain.*;
import com.example.acdc.service.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final DownloadService downloadService;
    private final SoundtrackService soundtrackService;
    private final UserService userService;
    private final SongService songService;
    private final SelectSoundtrackService selectSoundtrackService;

    @ModelAttribute("sessions")
    public Map<String, String> sessionModel(Model model) {
        Map<String, String> sessions = new LinkedHashMap<>();
        sessions.put("VOCAL", "Vocal");
        sessions.put("GUITAR", "Guitar");
        sessions.put("BASS", "Bass");
        sessions.put("DRUM", "Drum");
        sessions.put("KEYBOARD", "Keyboard");
        sessions.put("ETC", "Etc.");
        model.addAttribute("sessions", sessions);

        return sessions;
    }

    @GetMapping(value = "/search/{userId}")
    public String search(@PathVariable("userId") Long userId, Model model) {

        List<Soundtrack> soundtracks = new ArrayList<>();

        model.addAttribute("searchForm", new SearchForm());
        model.addAttribute("soundtracks", soundtracks);

        sessionModel(model);

        return "search_alternative";
    }

    @PostMapping(value = "/search/{userId}")
    public String searching(@PathVariable("userId") Long userId, SearchForm searchForm, Model model) {

        List<Song> songList = songService.findByTitle(searchForm.getSearchTarget());

        List<Soundtrack> soundtrackList = new ArrayList<>();

        for(int i = 0; i < songList.size(); i++){
            List<Soundtrack> tracks = soundtrackService.findBySong(songList.get(i));
            if(searchForm.searchOption != null) {
                if(searchForm.searchOption.contains("VOCAL")) {
                    soundtrackList.addAll(
                            tracks.stream()
                                    .filter(s -> s.getSession().equals(SessionState.VOCAL))
                                    .collect(Collectors.toList())
                    );
                }
                if(searchForm.searchOption.contains("GUITAR")) {
                    soundtrackList.addAll(
                            tracks.stream()
                                    .filter(s -> s.getSession().equals(SessionState.GUITAR))
                                    .collect(Collectors.toList())
                    );
                }
                if(searchForm.searchOption.contains("BASS")) {
                    soundtrackList.addAll(
                            tracks.stream()
                                    .filter(s -> s.getSession().equals(SessionState.BASS))
                                    .collect(Collectors.toList())
                    );
                }
                if(searchForm.searchOption.contains("DRUM")) {
                    soundtrackList.addAll(
                            tracks.stream()
                                    .filter(s -> s.getSession().equals(SessionState.DRUM))
                                    .collect(Collectors.toList())
                    );
                }
                if(searchForm.searchOption.contains("KEYBOARD")) {
                    soundtrackList.addAll(
                            tracks.stream()
                                    .filter(s -> s.getSession().equals(SessionState.KEYBOARD))
                                    .collect(Collectors.toList())
                    );
                }
                if(searchForm.searchOption.contains("ETC")) {
                    soundtrackList.addAll(
                            tracks.stream()
                                    .filter(s -> s.getSession().equals(SessionState.ETC))
                                    .collect(Collectors.toList())
                    );
                }
            }
        }
        model.addAttribute("soundtracks", soundtrackList);

        return "search_alternative";
    }

    @PostMapping(value = "/search/{userId}/{soundtrackId}/add")
    public String addSoundtrack(@PathVariable("userId") Long userId, @PathVariable("soundtrackId") Long soundtrackId,
                                HttpServletResponse response) throws Exception {

        Soundtrack soundtrack = soundtrackService.findOne(soundtrackId);
        User user = userService.findOne(userId);
        Download download = downloadService.getClosedOne(user);
        int isExist;

        SelectSoundtrack selectSoundtrack = SelectSoundtrack.createSelectSoundtrack(soundtrack);
        isExist = downloadService.addSelectSoundtrack(download, selectSoundtrack);
        if(isExist == 3) {
            ScriptUnit.alert(response, "이미 목록에 추가된 사운드트랙입니다.");
            return "redirect:/search/"+userId;
        }

        if(isExist == 1) {
            selectSoundtrackService.save(selectSoundtrack);
        }
        download.updateModifyDate();
        downloadService.save(download);
        userService.save(user);

        return "redirect:/search_alternative/"+userId;
    }
}
