package moment.hong.component.user.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignUpForm {
    private String email;
    private String userName;
    private String password;
    private String nickname;
    private String gender;

    @Builder
    public UserSignUpForm(String email, String userName, String password, String nickname, String gender) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
    }
}