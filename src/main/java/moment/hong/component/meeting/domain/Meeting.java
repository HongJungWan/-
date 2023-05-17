package moment.hong.component.meeting.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moment.hong.component.meeting.api.request.EditMeetingForm;
import moment.hong.component.meeting.domain.enumeration.MeetingStatus;
import moment.hong.component.meeting_image.domain.MeetingImage;
import moment.hong.core.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "meeting_image_id")
    private MeetingImage meetingImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "meeting_status")
    private MeetingStatus meetingStatus;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "description", length = 700)
    private String description;

    @Column(name = "meeting_place", length = 300)
    private String meetingPlace;

    @Column(name = "maximum_people")
    private int maximumPeople;

    @Column(name = "minimum_people")
    private int minimumPeople;

    @Column(name = "participants")
    private int participants;

    @Column(name = "start_date_time")
    private ZonedDateTime startDateTime;

    @Column(name = "end_date_time")
    private ZonedDateTime endDateTime;

    @Builder
    public Meeting(Long id, MeetingImage meetingImage, MeetingStatus meetingStatus, String title, String description,
                   String meetingPlace, int maximumPeople, int minimumPeople, int participants,
                   ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        this.id = id;
        this.meetingImage = meetingImage;
        this.meetingStatus = meetingStatus;
        this.title = title;
        this.description = description;
        this.meetingPlace = meetingPlace;
        this.maximumPeople = maximumPeople;
        this.minimumPeople = minimumPeople;
        this.participants = participants;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public void updateMeeting(EditMeetingForm editMeetingForm) {
        this.meetingStatus = editMeetingForm.getMeetingStatus();
        this.title = editMeetingForm.getTitle();
        this.description = editMeetingForm.getDescription();
        this.meetingPlace = editMeetingForm.getMeetingPlace();
        this.maximumPeople = editMeetingForm.getMaximumPeople();
        this.minimumPeople = editMeetingForm.getMinimumPeople();
        this.startDateTime = convertToZonedDateTime(editMeetingForm.getStartDateTime());
        this.endDateTime = convertToZonedDateTime(editMeetingForm.getEndDateTime());
    }

    public void addImage(MeetingImage meetingImage) {
        this.meetingImage = meetingImage;
    }

    public void updateStatus(MeetingStatus meetingStatus) {
        this.meetingStatus = meetingStatus;
    }

    public static ZonedDateTime convertToZonedDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atZone(ZoneId.systemDefault());
    }
}