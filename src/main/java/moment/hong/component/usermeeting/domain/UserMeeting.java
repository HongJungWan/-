package moment.hong.component.usermeeting.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moment.hong.component.meeting.domain.Meeting;
import moment.hong.component.user.domain.User;

import javax.persistence.*;

@Entity
@Table(name = "user_meeting")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    private boolean leader;

    @Builder
    public UserMeeting(User user, Meeting meeting, boolean leader) {
        this.user = user;
        this.meeting = meeting;
        this.leader = leader;
    }

    public static UserMeeting of(User user, Meeting meeting, boolean leader) {
        return UserMeeting.builder()
                .user(user)
                .meeting(meeting)
                .leader(true)
                .build();
    }
}