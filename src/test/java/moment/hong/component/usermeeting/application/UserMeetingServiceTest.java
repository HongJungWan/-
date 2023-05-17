package moment.hong.component.usermeeting.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moment.hong.component.meeting.domain.Meeting;
import moment.hong.component.meeting.domain.enumeration.MeetingStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Slf4j
public class UserMeetingServiceTest {
    private final UserMeetingService userMeetingService;
    @Mock
    Authentication authentication;
    Meeting meeting;

    @BeforeEach
    void setUp() {
        meeting = createMeeting();
    }

    @Test
    @DisplayName("로그인한 유저 id와 미팅 리더 id가 다르면 false 반환")
    void isUserIdEqualToMeetingUserIdFail() {
        //given & when
        boolean flag = userMeetingService.isUserIdEqualToMeetingUserId(authentication, meeting.getId());
        //then
        assertThat(flag).isFalse();
    }

    @Test
    @DisplayName("localDateTime -> null 값 입력 시, null 반환")
    public void convertToZonedDateTimeWithNull() {
        LocalDateTime localDateTime = null;
        ZonedDateTime result = userMeetingService.convertToZonedDateTime(localDateTime);

        assertNull(result, "The result should be null when the input is null");
    }

    @Test
    @DisplayName("localDateTime 값 입력 시, ZonedDateTime으로 변환 후 반환")
    public void convertToZonedDateTimeWithNonNull() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime result = userMeetingService.convertToZonedDateTime(localDateTime);

        assertEquals(ZoneId.systemDefault(), result.getZone(), "The timezone of the result should match the system default timezone");
        assertEquals(localDateTime, result.toLocalDateTime(), "The local date and time of the result should match the input local date and time");
    }

    private static Meeting createMeeting() {
        return Meeting.builder()
                .id(1L)
                .title("테스트 모임")
                .meetingStatus(MeetingStatus.AVAILABLE)
                .description("테스트 설명")
                .meetingPlace("테스트 장소")
                .maximumPeople(4)
                .minimumPeople(1)
                .participants(2)
                .startDateTime(null)
                .endDateTime(null)
                .build();
    }
}