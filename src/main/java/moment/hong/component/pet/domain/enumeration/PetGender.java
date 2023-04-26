package moment.hong.component.pet.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PetGender {
    MAN("수컷"),
    WOMAN("암컷"),
    PRIVATE("비공개");

    private final String description;
}