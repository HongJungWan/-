package moment.hong.component.usermeeting.repository;

import moment.hong.component.usermeeting.domain.UserMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMeetingRepository extends JpaRepository<UserMeeting, Long> {
    Optional<UserMeeting> findByUserIdAndMeetingId(Long userId, Long meetingId);
}