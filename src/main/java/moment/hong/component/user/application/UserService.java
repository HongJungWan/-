package moment.hong.component.user.application;

import lombok.RequiredArgsConstructor;
import moment.hong.component.user.api.request.UserLoginForm;
import moment.hong.component.user.api.request.UserSingUpForm;
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
    public UserDto join(UserSingUpForm userSingUpForm) {
        User user = userRepository.save(
                User.builder()
                        .userRole(UserRole.USER)
                        .gender(Gender.valueOf(userSingUpForm.getGender()))
                        .userName(userSingUpForm.getUserName())
                        .password(encoder.encode(userSingUpForm.getPassword()))
                        .nickname(userSingUpForm.getNickname())
                        .email(userSingUpForm.getEmail())
                        .build()
        );
        return UserDto.from(user);
    }

    @Transactional
    public String login(UserLoginForm userLoginForm) {
        User user = userRepository.findByEmail(userLoginForm.getEmail());
        matchesPasswordCheck(userLoginForm.getPassword(), user);
        return JwtTokenUtils.generateToken(userLoginForm.getEmail(), secretKey, expiredTimeMs);
    }

    public void matchesPasswordCheck(String password, User user) {
        if (!encoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException();
        }
    }
}