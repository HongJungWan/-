package moment.hong.component.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moment.hong.component.user.domain.Address;
import moment.hong.component.user.domain.User;
import moment.hong.component.user.domain.enumeration.Gender;
import moment.hong.component.user.domain.enumeration.UserRole;
import moment.hong.component.user.dto.UserDto;
import moment.hong.component.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Slf4j
public class UserServiceTest {
    private final UserService userService;
    private final UserRepository userRepository;
    Address address;

    @BeforeEach
    void setUp() {
        address = new Address("도시", "서울 강남구 테헤란로");
    }

    @Test
    void 회원가입() {
        //given & when
        User user = createUser();
        UserDto userDto = getJoin(user);
        //then
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDto.getNickname()).isEqualTo(user.getNickname());
        assertThat(userDto.getUserRole()).isEqualTo(user.getUserRole());
        assertThat(userDto.getUsername()).isEqualTo(user.getUserName());
    }

    @Test
    void 로그인() {
        //given & when
        User user = createUser();
        getJoin(user);
        String jwtToken = getLogin(user);
        //then
        assertThat(jwtToken).isNotNull();
        log.info(jwtToken.toString());
    }

    @Test
    void 패스워드_불일치() {
        // given
        User user = createUser();
        String invalidPassword = "에러 발생";

        // when & then
        assertAll("passwordCheck",
                () -> assertThrows(IllegalArgumentException.class,
                () -> userService.matchesPasswordCheck(invalidPassword, user))
        );
    }


    private User createUser() {
        return User.builder()
                .userRole(UserRole.USER)
                .gender(Gender.MAN)
                .userName("홍정완")
                .address(address)
                .password("123")
                .nickname("닉네임")
                .email("hongjungwan")
                .age(25)
                .selfIntroduction("최고의 개발자")
                .build();
    }

    private UserDto getJoin(User user) {
        return userService.join(
                user.getUserName(),
                user.getPassword(),
                user.getGender(),
                user.getAddress(),
                user.getNickname(),
                user.getEmail(),
                user.getAge(),
                user.getSelfIntroduction()
        );
    }

    private String getLogin(User user) {
        return userService.login(
                user.getEmail(),
                user.getPassword()
        );
    }
}