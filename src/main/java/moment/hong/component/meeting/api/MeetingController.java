package moment.hong.component.meeting.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moment.hong.component.meeting.api.request.CreateMeetingForm;
import moment.hong.component.meeting.api.request.EditMeetingForm;
import moment.hong.component.meeting.application.MeetingService;
import moment.hong.component.meeting.domain.Meeting;
import moment.hong.component.meeting.dto.MeetingDto;
import moment.hong.component.meeting_image.service.MeetingImageService;
import moment.hong.component.usermeeting.application.UserMeetingService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;
    private final UserMeetingService userMeetingService;
    private final MeetingImageService meetingImageService;

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
                                @RequestParam("path") MultipartFile path, Authentication authentication) {
        Meeting meeting = userMeetingService.userMeetingCreate(authentication, createMeetingForm);
        meetingImageService.create(path, meeting);
        return "redirect:/meetings/on";
    }

    @GetMapping("/{meetingId}")
    public String detailOffMeeting(Model model, @PathVariable Long meetingId, Authentication authentication) {
        if (userMeetingService.isUserIdEqualToMeetingUserId(authentication, meetingId)) {
            model.addAttribute("editMeetingForm", new EditMeetingForm());
            return "meetings/meetingDetailEdit";
        }
        MeetingDto meetingDto = meetingService.meeting(meetingId);
        model.addAttribute("meetingDto", meetingDto);
        return "meetings/meetingDetail";
    }

    @PostMapping("/{meetingId}/edit")
    public String detailOffMeetingEdit(Model model,
                                       @PathVariable Long meetingId,
                                       @ModelAttribute("editMeetingForm") EditMeetingForm editMeetingForm) {
        meetingService.updateMeeting(meetingId, editMeetingForm);
        return "redirect:/meetings/on";
    }
}