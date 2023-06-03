package moment.hong.component.meeting.application;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import moment.hong.component.meeting.api.request.EditMeetingForm;
import moment.hong.component.meeting.domain.Meeting;
import moment.hong.component.meeting.domain.QMeeting;
import moment.hong.component.meeting.dto.MeetingDto;
import moment.hong.component.meeting.repository.MeetingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<MeetingDto> searchOffMeeting(String searchTitle, Pageable pageable) {
        QMeeting meeting = QMeeting.meeting;
        BooleanBuilder builder = new BooleanBuilder();

        if (searchTitle != null) {
            builder.and(meeting.title.containsIgnoreCase(searchTitle));
        }
        Page<Meeting> meetingPage = meetingRepository.findAll(builder, pageable);
        return meetingPage.map(MeetingDto::toMeetingDto);
    }

    @Transactional
    public MeetingDto updateMeeting(Long meetingId, EditMeetingForm editMeetingForm) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 미팅 ID"));
        meeting.updateMeeting(editMeetingForm);
        return MeetingDto.toMeetingDto(meeting);
    }
}