package moment.hong.core.util.s3;

import lombok.RequiredArgsConstructor;
import moment.hong.component.meeting_image.domain.MeetingImage;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class S3Utils {
    private final S3Properties s3Properties;
    private S3Client s3Client;

    @PostConstruct
    public void setS3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey());
        s3Client = S3Client.builder()
                .region(Region.of(s3Properties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    public void deleteFromS3(MeetingImage meetingImage) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(meetingImage.getS3Key())
                .build());
    }

    public List<String> multiUploadToS3(List<MultipartFile> uploadFiles, String filename, String dirname) {
        return uploadFiles.stream()
                .map(file -> putS3(file, filename, dirname))
                .collect(Collectors.toList());
    }

    public String putS3(MultipartFile uploadFile, String filename, String dirname) {
        String s3Key = createS3Key(filename, dirname);
        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(s3Properties.getBucket())
                            .key(s3Key)
                            .acl(ObjectCannedACL.PUBLIC_READ)
                            .contentType(MediaType.IMAGE_PNG_VALUE)
                            .build(),
                    RequestBody.fromInputStream(uploadFile.getInputStream(), uploadFile.getSize()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s3Client.utilities().getUrl(GetUrlRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(s3Key)
                .build()).toString();
    }

    public String createS3Key(String filename, String dirname) {
        return dirname + "/" + filename;
    }

    public String createFilename(String origFilename) {
        return System.currentTimeMillis() + "_" + origFilename;
    }

    public String getImagePath(String path) {
        return s3Client.utilities().getUrl(GetUrlRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(path)
                .build()).toString();
    }
}