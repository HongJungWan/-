package moment.hong.component.meeting.application;

import lombok.RequiredArgsConstructor;
import moment.hong.component.meeting.api.request.EditMeetingForm;
import moment.hong.component.meeting.domain.Meeting;
import moment.hong.component.meeting.dto.MeetingDto;
import moment.hong.component.meeting.repository.MeetingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;

    @Transactional(readOnly = true)
    public MeetingDto meeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 meetingId가 전달 되었습니다."));
        return MeetingDto.toMeetingDto(meeting);
    }

    @Transactional(readOnly = true)
    public List<MeetingDto> searchOffMeeting(String searchTitle) {
        if (searchTitle == null) {
            return MeetingDto.toMeetingDtos(meetingRepository.findAll());
        }
        return MeetingDto.toMeetingDtos(meetingRepository.findByTitleContaining(searchTitle));
    }

    @Transactional
    public MeetingDto updateMeeting(Long meetingId, EditMeetingForm editMeetingForm) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 미팅 ID"));
        meeting.updateMeeting(editMeetingForm);
        return MeetingDto.toMeetingDto(meeting);
    }
}