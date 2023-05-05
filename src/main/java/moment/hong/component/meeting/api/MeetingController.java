package moment.hong.component.meeting.api;

import lombok.RequiredArgsConstructor;
import moment.hong.component.meeting.application.MeetingService;
import moment.hong.component.meeting.dto.MeetingDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    @GetMapping("/on")
    public String onMeeting(Model model) {
        List<MeetingDto> meetingDtoList = meetingService.meetings();
        model.addAttribute("meetingDtoList", meetingDtoList);
        return "meetings/meeting";
    }

    @GetMapping("/off")
    public String offMeeting(Model model) {
        List<MeetingDto> meetingDtoList = meetingService.meetings();
        model.addAttribute("meetingDtoList", meetingDtoList);
        return "meetings/meeting";
    }

    @GetMapping("/{meetingId}")
    public String DetailOnMeeting(@PathVariable Long meetingId, Model model) {
        MeetingDto meetingDto = meetingService.meeting(meetingId);
        model.addAttribute("meetingDto", meetingDto);
        return "meetings/meetingDetail";
    }
}