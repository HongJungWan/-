package moment.hong.component.meeting_image.service;

import lombok.RequiredArgsConstructor;
import moment.hong.component.meeting.domain.Meeting;
import moment.hong.component.meeting.repository.MeetingRepository;
import moment.hong.component.meeting_image.domain.MeetingImage;
import moment.hong.component.meeting_image.repository.MeetingImageRepository;
import moment.hong.core.util.s3.S3Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MeetingImageService {
    public static final String S3_ACCOUNT_DIR_NAME = "meeting";

    private final S3Utils s3Utils;
    private final MeetingImageRepository meetingImageRepository;
    private final MeetingRepository meetingRepository;

    @Transactional
    public void create(MultipartFile imageFile, Meeting meeting) {
        String origFilename = imageFile.getOriginalFilename();
        String filename = s3Utils.createFilename(origFilename);
        String imageUrl = s3Utils.putS3(imageFile, filename, S3_ACCOUNT_DIR_NAME);
        String s3Key = s3Utils.createS3Key(filename, S3_ACCOUNT_DIR_NAME);

        MeetingImage accountImage = createMeetingImage(origFilename, filename, imageUrl, s3Key);
        meetingImageRepository.save(accountImage);

        meeting.addImage(accountImage);
        meetingRepository.save(meeting);
    }

    @Transactional
    public void delete(Meeting meeting) {
        meetingImageRepository.delete(meeting.getMeetingImage());
        s3Utils.deleteFromS3(meeting.getMeetingImage());
    }

    private static MeetingImage createMeetingImage(String origFilename, String filename, String imageUrl, String s3Key) {
        return MeetingImage.builder()
                .name(filename)
                .originName(origFilename)
                .s3Key(s3Key)
                .path(imageUrl)
                .build();
    }
}