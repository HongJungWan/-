package moment.hong.component.meeting.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeetingStatus {
    AVAILABLE("참여 가능"),
    PERSON_FULL("인원 마감"),
    END("미팅 종료");

    private final String description;
}