package moment.hong.component.user.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MAN("남자"),
    WOMAN("여자"),
    PRIVATE("비공개");

    private final String value;
}
