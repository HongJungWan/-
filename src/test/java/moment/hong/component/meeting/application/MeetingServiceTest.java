package moment.hong.component.meeting.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moment.hong.component.meeting.domain.Meeting;
import moment.hong.component.meeting.domain.enumeration.MeetingStatus;
import moment.hong.component.meeting.dto.MeetingDto;
import moment.hong.component.meeting.repository.MeetingRepository;
import moment.hong.component.pet.domain.Age;
import moment.hong.component.user.domain.Address;
import moment.hong.component.user.domain.User;
import moment.hong.component.user.domain.enumeration.Gender;
import moment.hong.component.user.domain.enumeration.UserRole;
import moment.hong.component.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Slf4j
class MeetingServiceTest {
    private final MeetingService meetingService;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    Age age;
    Address address;
    Meeting meeting;
    Meeting save;

    @BeforeEach
    void setUp() {
        meeting = createMeeting();
        save = meetingRepository.save(meeting);
        age = new Age(20, 2032, 5);
        address = new Address("도시", "상세 주소");
        userRepository.save(createUser());
    }

    @Test
    @DisplayName("모임 상세 조회")
    void 모임_상세_조회() {
        //given & when
        MeetingDto meetingDto = meetingService.meeting(save.getId());
        //then
        모임_상세_조회_검증(meetingDto);
    }

    @Test
    @DisplayName("모임 상세 조회 실패")
    void 모임_상세_조회_실패() {
        //given & when & then
        assertThatThrownBy(() -> meetingService.meeting(9999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("잘못된 meetingId가 전달 되었습니다.");
    }

    @Test
    @DisplayName("검색어 존재 X -> 모임 전체 조회")
    void 모임_전체_조회() {
        //given & when
        List<MeetingDto> meetingDtos = meetingService.searchOffMeeting(null);
        //then
        모임_전체_조회_검증(meetingDtos);
    }

    @Test
    @DisplayName("검색어 존재 0 -> 검색어를 포함한 모임 전체 조회")
    void 모임_검색() {
        //given & when
        String searchTitle = "테스트";
        List<MeetingDto> meetingDtos = meetingService.searchOffMeeting(searchTitle);
        //then
        모임_검색_조회_검증(meetingDtos);
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

    private User createUser() {
        return User.builder()
                .userRole(UserRole.USER)
                .gender(Gender.MAN)
                .userName("홍정완")
                .address(address)
                .password("123")
                .nickname("닉네임")
                .email("hongjungwan")
                .age(age)
                .selfIntroduction("최고의 개발자")
                .build();
    }

    private static void 모임_상세_조회_검증(MeetingDto meetingDto) {
        assertThat(meetingDto.getId()).isEqualTo(1L);
        assertThat(meetingDto.getTitle()).isEqualTo("테스트 모임");
        assertThat(meetingDto.getMeetingStatus()).isEqualTo("참여 가능");
        assertThat(meetingDto.getDescription()).isEqualTo("테스트 설명");
        assertThat(meetingDto.getMeetingPlace()).isEqualTo("테스트 장소");
        assertThat(meetingDto.getMaximumPeople()).isEqualTo(4);
        assertThat(meetingDto.getMinimPeople()).isEqualTo(1);
        assertThat(meetingDto.getParticipant()).isEqualTo(2);
        assertThat(meetingDto.getStartDateTime()).isNull();
        assertThat(meetingDto.getEndDateTime()).isNull();
    }

    private static void 모임_전체_조회_검증(List<MeetingDto> meetingDtos) {
        assertThat(meetingDtos.get(0).getId()).isEqualTo(1L);
        assertThat(meetingDtos.get(0).getTitle()).isEqualTo("테스트 모임");
        assertThat(meetingDtos.get(0).getMeetingStatus()).isEqualTo("참여 가능");
        assertThat(meetingDtos.get(0).getDescription()).isEqualTo("테스트 설명");
        assertThat(meetingDtos.get(0).getMeetingPlace()).isEqualTo("테스트 장소");
        assertThat(meetingDtos.get(0).getMaximumPeople()).isEqualTo(4);
        assertThat(meetingDtos.get(0).getMinimPeople()).isEqualTo(1);
        assertThat(meetingDtos.get(0).getParticipant()).isEqualTo(2);
        assertThat(meetingDtos.get(0).getStartDateTime()).isNull();
        assertThat(meetingDtos.get(0).getEndDateTime()).isNull();
    }

    private static void 모임_검색_조회_검증(List<MeetingDto> meetingDtos) {
        assertThat(meetingDtos.get(0).getId()).isEqualTo(1L);
        assertThat(meetingDtos.get(0).getTitle()).isEqualTo("테스트 모임");
        assertThat(meetingDtos.get(0).getMeetingStatus()).isEqualTo("참여 가능");
        assertThat(meetingDtos.get(0).getDescription()).isEqualTo("테스트 설명");
        assertThat(meetingDtos.get(0).getMeetingPlace()).isEqualTo("테스트 장소");
        assertThat(meetingDtos.get(0).getMaximumPeople()).isEqualTo(4);
        assertThat(meetingDtos.get(0).getMinimPeople()).isEqualTo(1);
        assertThat(meetingDtos.get(0).getParticipant()).isEqualTo(2);
        assertThat(meetingDtos.get(0).getStartDateTime()).isNull();
        assertThat(meetingDtos.get(0).getEndDateTime()).isNull();
    }
}