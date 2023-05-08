package moment.hong.component.user.api;

import moment.hong.component.user.api.request.UserLoginForm;
import moment.hong.component.user.api.request.UserSignUpForm;
import moment.hong.component.user.domain.enumeration.Gender;
import moment.hong.component.user.repository.UserRepository;
import moment.hong.core.annotation.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("로그인 폼 조회 테스트")
    void login() throws Exception {
        //given & when & then
        mockMvc.perform(get("/users/login")
                        .param("userLoginForm", ""))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("로그인 시도 테스트")
    void testLogin() throws Exception {
        //given & when
        signUp();
        UserLoginForm userLoginForm = UserLoginForm.of("hong43ok@gmail.com", "123");
        //then
        mockMvc.perform(post("/users/login")
                        .param("email", userLoginForm.getEmail())
                        .param("password", userLoginForm.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("회원 가입 폼 조회 테스트")
    void singUp() throws Exception {
        //given & when & then
        mockMvc.perform(get("/users/sign-up")
                        .param("userSignUpForm", ""))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("회원 가입 등록 테스트")
    void signUp() throws Exception {
        //given & when
        UserSignUpForm userSignUpForm = createUserSignUpForm();
        //then
        mockMvc.perform(post("/users/sign-up")
                        .param("email", userSignUpForm.getEmail())
                        .param("userName", userSignUpForm.getUserName())
                        .param("password", userSignUpForm.getPassword())
                        .param("nickname", userSignUpForm.getNickname())
                        .param("gender", userSignUpForm.getGender()))
                .andExpect(status().is3xxRedirection())
                .andExpect(unauthenticated());
    }

    private static UserSignUpForm createUserSignUpForm() {
        return UserSignUpForm.builder()
                .email("hong43ok@gmail.com")
                .userName("홍정완")
                .password("123")
                .nickname("닉네임")
                .gender(String.valueOf(Gender.MAN))
                .build();
    }
}