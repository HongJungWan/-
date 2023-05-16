package moment.hong.component.meeting.api;

import moment.hong.component.meeting.api.request.EditMeetingForm;
import moment.hong.component.meeting.domain.Meeting;
import moment.hong.component.meeting.domain.enumeration.MeetingStatus;
import moment.hong.component.meeting.repository.MeetingRepository;
import moment.hong.core.annotation.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    @WithMockUser
    @DisplayName("모임 개설자가 본인의 모임을 상세 조회 시, 모임 상세 수정 페이지 리다이렉트")
    void 모임개설자_모임_상세_조회() throws Exception {
        //given
        Meeting meeting = createMeeting();
        Meeting meetingTest = meetingRepository.save(meeting);
        //when & then
        mockMvc.perform(get("/meetings/{meetingId}", meetingTest.getId()))
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }

    @Test
    @DisplayName("일반유저가 모임 상세 조회 시, 모임 상세 페이지")
    void 일반유저_모임_상세_조회() throws Exception {
        //given
        Meeting meeting = createMeeting();
        Meeting meetingTest = meetingRepository.save(meeting);
        //when & then
        mockMvc.perform(get("/meetings/{meetingId}", meetingTest.getId()))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    @DisplayName("모임 생성 폼")
    void 모임_생성_폼() throws Exception {
        //given & when & then
        mockMvc.perform(get("/meetings/on/create"))
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }

    @Test
    @WithMockUser
    @DisplayName("모임 생성")
    void 모임_생성() throws Exception {
        //given & when & then
        mockMvc.perform(post("/meetings/on/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated());
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
    @DisplayName("모임 상세 조회 - 예외")
    void detailOffMeeting() throws Exception {
        //given & when & then
        mockMvc.perform(get("/meetings/{meetingId}", 9999L))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("잘못된 meetingId가 전달 되었습니다."))
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    @DisplayName("모임 상세 내용 수정")
    void detailOffMeetingEdit() throws Exception {
        //given
        EditMeetingForm editMeetingForm = createEditMeetingForm();
        Meeting meetingTest = meetingRepository.save(createMeeting());
        //when & then
        mockMvc.perform(post("/meetings/{meetingId}/edit", meetingTest.getId())
                        .flashAttr("editMeetingForm", editMeetingForm)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated());
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

    private static EditMeetingForm createEditMeetingForm() {
        return EditMeetingForm.builder()
                .meetingStatus(MeetingStatus.END)
                .title("모임 상세 제목 수정")
                .description("모임 상세 내용 수정")
                .meetingPlace("모임 상세 장소 수정")
                .maximumPeople(3)
                .minimumPeople(1)
                .startDateTime(null)
                .endDateTime(null)
                .build();
    }
}