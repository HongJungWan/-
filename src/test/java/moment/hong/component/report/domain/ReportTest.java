package moment.hong.component.report.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ReportTest {

    @Test
    void 신고_생성_테스트() {
        //given & when
        Report report = createReport();
        //then
        신고_생성_검증(report);
    }

    private static Report createReport() {
        return Report.builder()
                .reporterUserId(1L) // 신고한 사람
                .reportedUserId(10L) // 신고 당한 사람
                .reportedMeetingId(100L) // 신고 당한 모임
                .reportedCommentId(1000L) // 신고 당한 댓글
                .reason("강아지 게시판에 고양이 사진 올림")
                .deleted(false)
                .build();
    }

    private static void 신고_생성_검증(Report report) {
        assertThat(report.getId()).isNull();
        assertThat(report.getReporterUserId()).isEqualTo(1L);
        assertThat(report.getReportedUserId()).isEqualTo(10);
        assertThat(report.getReportedMeetingId()).isEqualTo(100L);
        assertThat(report.getReportedCommentId()).isEqualTo(1000L);
        assertThat(report.getReason()).isEqualTo("강아지 게시판에 고양이 사진 올림");
        assertThat(report.getDeleted()).isFalse();
    }
}