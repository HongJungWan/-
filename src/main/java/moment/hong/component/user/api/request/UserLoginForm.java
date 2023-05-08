package moment.hong.component.user.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginForm {
    private String email;
    private String password;

    public static UserLoginForm of(String email, String password) {
        return new UserLoginForm(email, password);
    }
}