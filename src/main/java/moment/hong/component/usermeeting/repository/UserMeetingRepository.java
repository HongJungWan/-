package moment.hong.component.usermeeting.repository;

import moment.hong.component.usermeeting.domain.UserMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMeetingRepository extends JpaRepository<UserMeeting, Long> {
}