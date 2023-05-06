package moment.hong.component.meeting.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moment.hong.component.meeting.application.MeetingService;
import moment.hong.component.meeting.dto.MeetingDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping("/on")
    public String onMeeting(Model model, @RequestParam(required = false) String titleSearch) {
        List<MeetingDto> meetingDtoList = meetingService.searchOffMeeting(titleSearch);
        model.addAttribute("meetingDtoList", meetingDtoList);
        return "meetings/meeting";
    }

    @GetMapping("/off")
    public String offMeeting(Model model, @RequestParam(required = false) String titleSearch) {
        List<MeetingDto> meetingDtoList = meetingService.searchOffMeeting(titleSearch);
        model.addAttribute("meetingDtoList", meetingDtoList);
        return "meetings/meeting";
    }

    @GetMapping("/{meetingId}")
    public String detailOffMeeting(@PathVariable Long meetingId, Model model) {
        MeetingDto meetingDto = meetingService.meeting(meetingId);
        model.addAttribute("meetingDto", meetingDto);
        return "meetings/meetingDetail";
    }
}