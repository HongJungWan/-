package moment.hong.component.meeting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moment.hong.component.meeting.domain.Meeting;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MeetingDto {
    private Long id;
    private String meetingStatus;
    private String title;
    private String description;
    private String meetingPlace;
    private String path;
    private int maximumPeople;
    private int minimPeople;
    private int participant;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;

    @Builder
    public MeetingDto(Long id, String meetingStatus, String title, String description, String meetingPlace,
                      int maximumPeople, int minimPeople, int participant, ZonedDateTime startDateTime,
                      ZonedDateTime endDateTime, String path) {
        this.id = id;
        this.meetingStatus = meetingStatus;
        this.title = title;
        this.description = description;
        this.meetingPlace = meetingPlace;
        this.path = path;
        this.maximumPeople = maximumPeople;
        this.minimPeople = minimPeople;
        this.participant = participant;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public static List<MeetingDto> toMeetingDtos(List<Meeting> meetings) {
        return meetings.stream()
                .map(meeting -> MeetingDto.builder()
                        .id(meeting.getId())
                        .meetingStatus(meeting.getMeetingStatus().getDescription())
                        .title(meeting.getTitle())
                        .description(meeting.getDescription())
                        .meetingPlace(meeting.getMeetingPlace())
                        .path(meeting.getMeetingImage().getPath())
                        .maximumPeople(meeting.getMaximumPeople())
                        .minimPeople(meeting.getMinimumPeople())
                        .participant(meeting.getParticipants())
                        .startDateTime(meeting.getStartDateTime())
                        .endDateTime(meeting.getEndDateTime())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public static MeetingDto toMeetingDto(Meeting meeting) {
        return MeetingDto.builder()
                .id(meeting.getId())
                .meetingStatus(meeting.getMeetingStatus().getDescription())
                .title(meeting.getTitle())
                .description(meeting.getDescription())
                .meetingPlace(meeting.getMeetingPlace())
                .path(meeting.getMeetingImage().getPath())
                .maximumPeople(meeting.getMaximumPeople())
                .minimPeople(meeting.getMinimumPeople())
                .participant(meeting.getParticipants())
                .startDateTime(meeting.getStartDateTime())
                .endDateTime(meeting.getEndDateTime())
                .build();
    }
}