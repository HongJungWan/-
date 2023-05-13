package moment.hong.component.usermeeting.application;

import lombok.RequiredArgsConstructor;
import moment.hong.component.meeting.api.request.CreateMeetingForm;
import moment.hong.component.meeting.domain.Meeting;
import moment.hong.component.meeting.repository.MeetingRepository;
import moment.hong.component.user.domain.User;
import moment.hong.component.user.repository.UserRepository;
import moment.hong.component.usermeeting.domain.UserMeeting;
import moment.hong.component.usermeeting.repository.UserMeetingRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserMeetingService {
    private final UserMeetingRepository userMeetingRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Transactional
    public void UserMeetingCreate(Authentication authentication, CreateMeetingForm createMeetingForm) {
        User user = userRepository.findByUserName(authentication.getName());
        Meeting meeting = createMeeting(createMeetingForm);

        meetingRepository.save(meeting);
        userMeetingRepository.save(createUserMeeting(user, meeting));
    }

    @Transactional
    public boolean isUserIdEqualToMeetingUserId(Authentication authentication, Long meetingId) {
        if (authentication == null || authentication.getName() == null) {
            return false;
        }
        User user = userRepository.findByUserName(authentication.getName());
        if (user == null) {
            return false;
        }
        Optional<UserMeeting> userMeeting = userMeetingRepository.findByUserIdAndMeetingId(user.getId(), meetingId);
        return userMeeting.isPresent();
    }

    private static UserMeeting createUserMeeting(User user, Meeting meeting) {
        return UserMeeting.builder()
                .user(user)
                .meeting(meeting)
                .leader(true)
                .build();
    }

    private static Meeting createMeeting(CreateMeetingForm createMeetingForm) {
        return Meeting.builder()
                .meetingStatus(createMeetingForm.getMeetingStatus())
                .title(createMeetingForm.getTitle())
                .description(createMeetingForm.getDescription())
                .meetingPlace(createMeetingForm.getMeetingPlace())
                .maximumPeople(createMeetingForm.getMaximumPeople())
                .minimumPeople(createMeetingForm.getMinimumPeople())
                .startDateTime(convertToZonedDateTime(createMeetingForm.getStartDateTime()))
                .endDateTime(convertToZonedDateTime(createMeetingForm.getEndDateTime()))
                .build();
    }

    private static ZonedDateTime convertToZonedDateTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault());
    }
}