package moment.hong.component.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moment.hong.component.pet.domain.Age;
import moment.hong.component.user.api.request.UserLoginForm;
import moment.hong.component.user.api.request.UserSignUpForm;
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
    Age age;

    @BeforeEach
    void setUp() {
        this.address = new Address();
        this.address = new Address("도시", "서울 강남구 테헤란로");
        this.age = new Age(15, 2023, 4);
    }

    @Test
    void 회원가입() {
        //given & when
        UserDto userDto = getJoin(createUserSignUpForm());
        //then
        회원가입_검증(userDto);
    }

    @Test
    void 로그인() {
        //given & when
        getJoin(createUserSignUpForm());
        UserLoginForm userLoginForm = UserLoginForm.of(createUser().getEmail(), createUser().getPassword());
        String jwtToken = getLogin(userLoginForm);
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
                .age(age)
                .selfIntroduction("최고의 개발자")
                .build();
    }

    private static UserSignUpForm createUserSignUpForm() {
        return UserSignUpForm.builder()
                .userName("홍정완")
                .password("123")
                .gender(String.valueOf(Gender.MAN))
                .nickname("닉네임")
                .email("hongjungwan")
                .build();
    }

    private UserDto getJoin(UserSignUpForm userSignUpForm) {
        return userService.join(userSignUpForm);
    }

    private String getLogin(UserLoginForm userLoginForm) {
        return userService.login(userLoginForm);
    }

    private static void 회원가입_검증(UserDto userDto) {
        assertThat(userDto.getEmail()).isEqualTo(createUserSignUpForm().getEmail());
        assertThat(userDto.getNickname()).isEqualTo(createUserSignUpForm().getNickname());
        assertThat(userDto.getUsername()).isEqualTo(createUserSignUpForm().getUserName());
    }
}