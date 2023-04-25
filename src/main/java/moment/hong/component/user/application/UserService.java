package moment.hong.component.user.application;

import lombok.RequiredArgsConstructor;
import moment.hong.component.user.domain.Address;
import moment.hong.component.user.domain.User;
import moment.hong.component.user.domain.enumeration.Gender;
import moment.hong.component.user.domain.enumeration.UserRole;
import moment.hong.component.user.dto.UserDto;
import moment.hong.component.user.repository.UserRepository;
import moment.hong.core.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Transactional
    public UserDto join(String userName, String password, Gender gender, Address address, String nickname,
                        String email, Integer age, String selfIntroduction) {
        User user = userRepository.save(
                User.builder()
                        .userRole(UserRole.USER)
                        .gender(gender)
                        .userName(userName)
                        .password(encoder.encode(password))
                        .address(address)
                        .nickname(nickname)
                        .email(email)
                        .age(age)
                        .selfIntroduction(selfIntroduction)
                        .build()
        );
        return UserDto.from(user);
    }

    @Transactional
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);;
        matchesPasswordCheck(password, user);
        return JwtTokenUtils.generateToken(email, secretKey, expiredTimeMs);
    }

    public void matchesPasswordCheck(String password, User user) {
        if (!encoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException();
        }
    }
}