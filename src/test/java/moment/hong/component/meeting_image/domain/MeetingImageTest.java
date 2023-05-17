package moment.hong.component.meeting_image.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MeetingImageTest {
    @Test
    @DisplayName("미팅_이미지_생성_테스트")
    void 미팅_이미지_생성_테스트() {
        //given & when
        MeetingImage meetingImage = createMeetingImage();
        //then
        assertThat(meetingImage.getOriginName()).isEqualTo("원본 이미지 이름");
        assertThat(meetingImage.getName()).isEqualTo("이미지 이름");
        assertThat(meetingImage.getS3Key()).isEqualTo("S3 키");
        assertThat(meetingImage.getPath()).isEqualTo("이미지 경로");
    }

    @Test
    @DisplayName("미팅_이미지_업데이트_테스트")
    void 미팅_이미지_업데이트_테스트() {
        //given & when
        MeetingImage meetingImage = new MeetingImage();
        meetingImage.update("수정 이미지 이름", "수정 이미지 이름", "수정 S3 키", "수정 이미지 경로");
        //then
        assertThat(meetingImage.getOriginName()).isEqualTo("수정 이미지 이름");
        assertThat(meetingImage.getName()).isEqualTo("수정 이미지 이름");
        assertThat(meetingImage.getS3Key()).isEqualTo("수정 S3 키");
        assertThat(meetingImage.getPath()).isEqualTo("수정 이미지 경로");
    }

    private static MeetingImage createMeetingImage() {
        return MeetingImage.builder()
                .originName("원본 이미지 이름")
                .name("이미지 이름")
                .s3Key("S3 키")
                .path("이미지 경로")
                .build();
    }
}