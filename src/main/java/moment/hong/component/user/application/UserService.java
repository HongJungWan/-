package moment.hong.component.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moment.hong.component.user.api.request.UserLoginForm;
import moment.hong.component.user.api.request.UserSignUpForm;
import moment.hong.component.user.domain.User;
import moment.hong.component.user.domain.enumeration.Gender;
import moment.hong.component.user.domain.enumeration.UserRole;
import moment.hong.component.user.dto.UserDto;
import moment.hong.component.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static moment.hong.core.config.SecurityConfig.passwordEncoder;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto join(UserSignUpForm userSignUpForm) {
        Optional<User> userFind = Optional.ofNullable(userRepository.findByEmail(userSignUpForm.getEmail()));
        if (!userFind.isEmpty()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        User userSaved = userRepository.save(createUser(userSignUpForm));
        return UserDto.from(userSaved);
    }

    @Transactional
    public UserDto login(UserLoginForm userLoginForm) {
        UserDto userDto = (UserDto) loadUserByUsername(userLoginForm.getEmail());
        log.info(userDto.getEmail());
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> findMember = Optional.ofNullable(userRepository.findByEmail(email));
        if (findMember.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 email 입니다.");
        }
        return UserDto.from(findMember.get());
    }

    private User createUser(UserSignUpForm userSignUpForm) {
        return User.builder()
                .userRole(UserRole.USER)
                .gender(Gender.valueOf(userSignUpForm.getGender()))
                .userName(userSignUpForm.getUserName())
                .password(passwordEncoder().encode(userSignUpForm.getPassword()))
                .nickname(userSignUpForm.getNickname())
                .email(userSignUpForm.getEmail())
                .build();
    }
}