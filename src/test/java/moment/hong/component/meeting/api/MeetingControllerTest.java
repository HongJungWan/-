package moment.hong.component.meeting.api;

import moment.hong.component.meeting.domain.Meeting;
import moment.hong.component.meeting.domain.enumeration.MeetingStatus;
import moment.hong.component.meeting.repository.MeetingRepository;
import moment.hong.core.annotation.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class MeetingControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    MeetingRepository meetingRepository;

    @Test
    @DisplayName("검색어 존재 X -> 모임 전체 조회")
    void 모임_전체_조회() throws Exception {
        //given & when & then
        mockMvc.perform(get("/meetings/on")
                        .param("titleSearch", ""))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("검색어 존재 0 -> 검색어 포함 모임 전체 조회")
    void 검색어_포함_모임_전체_조회() throws Exception {
        //given & when & then
        mockMvc.perform(get("/meetings/on")
                        .param("titleSearch", "모임"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("모임 상세 조회")
    void detailOffMeeting() throws Exception {
        //given
        Meeting meeting = createMeeting();
        meetingRepository.save(meeting);
        //when & then
        mockMvc.perform(get("/meetings/{meetingId}", 1L))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());
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