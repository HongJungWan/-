package moment.hong.component.meeting.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moment.hong.component.meeting.domain.enumeration.MeetingStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EditMeetingForm {
    private MeetingStatus meetingStatus;
    private String title;
    private String description;
    private String meetingPlace;
    private int maximumPeople;
    private int minimumPeople;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDateTime;
}