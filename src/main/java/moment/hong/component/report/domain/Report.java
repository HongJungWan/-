package moment.hong.component.report.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moment.hong.core.common.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update report set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class Report extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reporterUserId;

    private Long reportedUserId;

    private Long reportedMeetingId;

    private Long reportedCommentId;

    @Column(length = 100)
    private String reason;

    private Boolean deleted;

    @Builder
    public Report(Long reporterUserId, Long reportedUserId, Long reportedMeetingId, Long reportedCommentId,
                  String reason, Boolean deleted) {
        this.reporterUserId = reporterUserId;
        this.reportedUserId = reportedUserId;
        this.reportedMeetingId = reportedMeetingId;
        this.reportedCommentId = reportedCommentId;
        this.reason = reason;
        this.deleted = deleted;
    }
}