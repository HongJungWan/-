package moment.hong.component.usermeeting.domain;

import moment.hong.component.meeting.domain.Meeting;
import moment.hong.component.meeting.domain.enumeration.MeetingStatus;
import moment.hong.component.pet.domain.Age;
import moment.hong.component.user.domain.Address;
import moment.hong.component.user.domain.User;
import moment.hong.component.user.domain.enumeration.Gender;
import moment.hong.component.user.domain.enumeration.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMeetingTest {
    private Address address;
    private Age age;
    private MeetingStatus meetingStatus;

    @BeforeEach
    void setUp() {
        age = new Age(25, 2023, 3);
        address = new Address("도시", "상세 주소");
        meetingStatus = MeetingStatus.AVAILABLE;
    }

    @Test
    void 유저_미팅_생성_테스트() {
        //given & when
        UserMeeting userMeeting = createUserMeeting();
        //then
        유저_미팅_생성_검증(userMeeting);
    }

    @Test
    void 유저_미팅_생성_정적팩토리() {
        //given & when
        UserMeeting userMeeting = UserMeeting.of(createUser(), createMeeting(), true);
        //then
        유저_미팅_생성_검증(userMeeting);
    }

    private User createUser() {
        return User.builder()
                .userRole(UserRole.USER)
                .gender(Gender.MAN)
                .userName("홍정완")
                .password("비밀번호")
                .address(address)
                .nickname("닉네임")
                .email("hongjungwan")
                .age(age)
                .selfIntroduction("최고의 개발자")
                .build();
    }

    private Meeting createMeeting() {
        return Meeting.builder()
                .meetingStatus(meetingStatus)
                .title("최고의 개발자")
                .description("꽤 괜찮은 개발자가 되기 위해 이것저것 합니다.")
                .meetingPlace("상암")
                .maximumPeople(4)
                .minimumPeople(1)
                .participants(3)
                .startDateTime(null)
                .endDateTime(null)
                .build();
    }

    private UserMeeting createUserMeeting() {
        return UserMeeting.builder()
                .user(createUser())
                .meeting(createMeeting())
                .build();
    }

    private static void 유저_미팅_생성_검증(UserMeeting userMeeting) {
        assertNotNull(userMeeting);
        assertThat(userMeeting.getId()).isNull();
        assertThat(userMeeting.getUser().getEmail()).isEqualTo("hongjungwan");
        assertThat(userMeeting.getMeeting().getTitle()).isEqualTo("최고의 개발자");
    }
}