package moment.hong.component.meeting.application;

import lombok.RequiredArgsConstructor;
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
    public List<MeetingDto> meetings() {
        return MeetingDto.toMeetingDtos(meetingRepository.findAll());
    }

    @Transactional(readOnly = true)
    public MeetingDto meeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 meetingId가 전달 되었습니다."));
        return MeetingDto.toMeetingDto(meeting);
    }
}