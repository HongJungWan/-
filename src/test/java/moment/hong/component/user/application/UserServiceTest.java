package moment.hong.component.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moment.hong.component.pet.domain.Age;
import moment.hong.component.user.api.request.UserLoginForm;
import moment.hong.component.user.api.request.UserSignUpForm;
import moment.hong.component.user.domain.Address;
import moment.hong.component.user.domain.User;
import moment.hong.component.user.domain.enumeration.Gender;
import moment.hong.component.user.dto.UserDto;
import moment.hong.component.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        UserSignUpForm userSignUpFormJoin = getUserSignUpFormJoin();
        UserDto userJoin = userService.join(userSignUpFormJoin);
        //then
        assertThat(userJoin.getEmail()).isEqualTo(userSignUpFormJoin.getEmail());
        assertThat(userJoin.getNickname()).isEqualTo(userSignUpFormJoin.getNickname());
        assertThat(userJoin.getUsername()).isEqualTo(userSignUpFormJoin.getUserName());
    }

    @Test
    @DisplayName("중복 이메일은 회원가입을 실패한다.")
    void 회원가입_실패() {
        //given & when
        User user = userCreate();
        userRepository.save(user);

        UserSignUpForm JoinException = JoinExceptionCreate();
        //then
        assertThatThrownBy(() -> userService.join(JoinException))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 사용 중인 이메일입니다.");
    }

    @Test
    void 로그인() {
        //given
        UserSignUpForm userSignUpFormLogin = getUserSignUpFormLogin();
        userService.join(userSignUpFormLogin);
        //when
        UserLoginForm userLoginForm = UserLoginForm.of(userSignUpFormLogin.getEmail(), userSignUpFormLogin.getPassword());
        UserDto userLogin = userService.login(userLoginForm);
        //then
        assertThat(userLogin.getEmail()).isEqualTo("hong");
    }

    @Test
    @DisplayName("존재하지 않는 email은 로그인에 실패한다.")
    void 로그인_실패() {
        //given & when
        //then
        assertThatThrownBy(() -> userService.loadUserByUsername(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 email 입니다.");
    }

    private static UserSignUpForm getUserSignUpFormJoin() {
        return UserSignUpForm.builder()
                .userName("홍정완")
                .password("123")
                .gender(String.valueOf(Gender.MAN))
                .nickname("닉네임")
                .email("textEmail")
                .build();
    }

    private static UserSignUpForm getUserSignUpFormLogin() {
        return UserSignUpForm.builder()
                .userName("홍정완")
                .password("123")
                .gender(String.valueOf(Gender.MAN))
                .nickname("닉네임")
                .email("hong")
                .build();
    }

    private static User userCreate() {
        return User.builder()
                .userName("홍정완")
                .password("123")
                .gender(Gender.valueOf(String.valueOf(Gender.MAN)))
                .nickname("닉네임")
                .email("textEmail2")
                .build();
    }

    private static UserSignUpForm JoinExceptionCreate() {
        return UserSignUpForm.builder()
                .userName("홍정완")
                .password("123")
                .gender(String.valueOf(Gender.MAN))
                .nickname("닉네임")
                .email("textEmail2")
                .build();
    }
}