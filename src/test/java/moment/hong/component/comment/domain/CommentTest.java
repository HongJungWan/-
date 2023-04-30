package moment.hong.component.comment.domain;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
class CommentTest {
    private Age age;
    private Address address;
    private User user;
    private Meeting meeting;

    @BeforeEach
    void setUp() {
        age = new Age(10, 2023, 4);
        address = new Address("도시", "상세주소");
        user = createUser();
        meeting = createMeeting();
    }

    @Test
    void 댓글_생성_테스트() {
        //given & when
        Comment comment = createComment();
        //then
        assertThat(comment.getId()).isNull();
        assertThat(comment.getContent()).isEqualTo("댓글 테스트");
        assertThat(comment.getUser()).isNotNull();
        assertThat(comment.getUser().getEmail()).isEqualTo("hongjungwan");
        assertThat(comment.getMeeting().getTitle()).isEqualTo("최고의 개발자");
        assertThat(comment.getMeeting()).isNotNull();
    }

    private Comment createComment() {
        return Comment.builder()
                .content("댓글 테스트")
                .user(user)
                .meeting(meeting)
                .build();
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
                .meetingStatus(MeetingStatus.AVAILABLE)
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
}