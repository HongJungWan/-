package moment.hong.component.meeting.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moment.hong.component.meeting.domain.enumeration.MeetingStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CreateMeetingForm {
    private MeetingStatus meetingStatus = MeetingStatus.AVAILABLE;
    private String title;
    private String description;
    private String meetingPlace;
    private MultipartFile path;
    private int maximumPeople;
    private int minimumPeople;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDateTime;

    @Builder
    public CreateMeetingForm(MeetingStatus meetingStatus, String title, String description, String meetingPlace,
                             int maximumPeople, int minimumPeople, LocalDateTime startDateTime,
                             LocalDateTime endDateTime, MultipartFile path) {
        this.meetingStatus = meetingStatus;
        this.title = title;
        this.description = description;
        this.meetingPlace = meetingPlace;
        this.path = path;
        this.maximumPeople = maximumPeople;
        this.minimumPeople = minimumPeople;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}