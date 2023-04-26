package moment.hong.component.user.domain;

import moment.hong.component.user.domain.enumeration.Gender;
import moment.hong.component.user.domain.enumeration.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address("도시", "상세 주소");
    }

    @Test
    void 유저_생성() {
        //given & when
        User user = createUser();
        //then
        assertThat(user).isNotNull();
        assertThat(user.getUserRole()).isEqualTo(UserRole.USER);
        assertThat(user.getUserRole().getValue()).isEqualTo("회원");
        assertThat(user.getGender()).isEqualTo(Gender.MAN);
        assertThat(user.getGender().getValue()).isEqualTo("남자");
        assertThat(user.getAddress().getCity()).isEqualTo("도시");
        assertThat(user.getAddress().getDetail()).isEqualTo("상세 주소");
        assertThat(user.getUserName()).isEqualTo("홍정완");
        assertThat(user.getPassword()).isEqualTo("비밀번호");
        assertThat(user.getEmail()).isEqualTo("hongjungwan");
        assertThat(user.getAge()).isEqualTo(25);
        assertThat(user.getSelfIntroduction()).isEqualTo("최고의 개발자");
        assertThat(user.getAge()).isEqualTo(25);
    }

    @Test
    void 주소_업데이트() {
        //given & when
        address.updateCity("도시 업데이트");
        address.updateDetail("상세 주소 업데이트");
        //then
        assertThat(address.getCity()).isEqualTo("도시 업데이트");
        assertThat(address.getDetail()).isEqualTo("상세 주소 업데이트");
    }

    @Test
    void 회원가입() {
        //given
        User user = signUpUser();
        User signUpTest = new User();
        //when
        signUpTest.signUp(user);
        //then
        assertThat(signUpTest).isNotNull();
        assertThat(signUpTest.toString()).isNotNull();
        assertThat(signUpTest.getGender()).isEqualTo(Gender.MAN);
        assertThat(signUpTest.getAddress()).isEqualTo(address);
        assertThat(signUpTest.getNickname()).isEqualTo("닉네임");
        assertThat(signUpTest.getEmail()).isEqualTo("hongjungwan");
        assertThat(signUpTest.getAge()).isEqualTo(25);
        assertThat(signUpTest.getSelfIntroduction()).isEqualTo("최고의 개발자");
    }

    private User createUser() {
        return User.builder()
                .userRole(UserRole.USER)
                .gender(Gender.MAN)
                .userName("홍정완")
                .password("비밀번호")
                .address(address)
                .nickname("닉네임")
                .email("hongjungwan")
                .age(25)
                .selfIntroduction("최고의 개발자")
                .build();
    }

    private User signUpUser() {
        return User.builder()
                .gender(Gender.MAN)
                .address(address)
                .nickname("닉네임")
                .email("hongjungwan")
                .age(25)
                .selfIntroduction("최고의 개발자")
                .build();
    }
}