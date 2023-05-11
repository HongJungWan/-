package moment.hong.component.meeting.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moment.hong.component.meeting.api.request.CreateMeetingForm;
import moment.hong.component.meeting.application.MeetingService;
import moment.hong.component.meeting.dto.MeetingDto;
import moment.hong.component.usermeeting.application.UserMeetingService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;
    private final UserMeetingService userMeetingService;

    @GetMapping("/on")
    public String onMeeting(Model model, @RequestParam(required = false) String titleSearch) {
        List<MeetingDto> meetingDtoList = meetingService.searchOffMeeting(titleSearch);
        model.addAttribute("meetingDtoList", meetingDtoList);
        return "meetings/meeting";
    }

    @GetMapping("/on/create")
    public String createMeeting(Model model) {
        model.addAttribute("createMeetingForm", new CreateMeetingForm());
        return "meetings/create-meeting";
    }

    @PostMapping("/on/create")
    public String createMeeting(Model model, @ModelAttribute("createMeetingForm") CreateMeetingForm createMeetingForm,
                                Authentication authentication) {
        userMeetingService.UserMeetingCreate(authentication, createMeetingForm);
        return "redirect:/meetings/on";
    }

    @GetMapping("/{meetingId}")
    public String detailOffMeeting(@PathVariable Long meetingId, Model model) {
        MeetingDto meetingDto = meetingService.meeting(meetingId);
        model.addAttribute("meetingDto", meetingDto);
        return "meetings/meetingDetail";
    }
}