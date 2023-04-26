package moment.hong.component.pet.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SizeType {
    SMALL("소형견"),
    MEDIUM("중형견"),
    LARGE("대형견");

    private final String description;
}