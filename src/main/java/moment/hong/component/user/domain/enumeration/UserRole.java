package moment.hong.component.user.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN("관리자"),
    USER("회원"),
    GUEST("게스트");

    private final String value;
}