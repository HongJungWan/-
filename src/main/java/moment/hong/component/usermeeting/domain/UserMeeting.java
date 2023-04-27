package moment.hong.component.usermeeting.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moment.hong.component.meeting.domain.Meeting;
import moment.hong.component.user.domain.User;
import moment.hong.core.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "user_meeting")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMeeting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Builder
    public UserMeeting(User user, Meeting meeting) {
        this.user = user;
        this.meeting = meeting;
    }

    public static UserMeeting of(User user, Meeting meeting) {
        return UserMeeting.builder()
                .user(user)
                .meeting(meeting)
                .build();
    }
}