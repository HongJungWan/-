package moment.hong.component.pet.domain;

import lombok.extern.slf4j.Slf4j;
import moment.hong.component.pet.domain.enumeration.PetGender;
import moment.hong.component.pet.domain.enumeration.SizeType;
import moment.hong.component.user.domain.Address;
import moment.hong.component.user.domain.User;
import moment.hong.component.user.domain.enumeration.Gender;
import moment.hong.component.user.domain.enumeration.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class PetTest {
    private Age age;
    private User user;
    private Address address;

    @BeforeEach
    void setUp() {
        age = new Age();
        age = new Age(10, 2023, 4);
        address = new Address("도시", "상세주소");
        user = createUser();
    }

    @Test
    void 펫_정보_생성() {
        //given & when
        Pet pet = createPet();
        //then
        assertThat(pet).isNotNull();
        assertThat(pet.getUser().getUserName()).isEqualTo("홍정완");
        assertThat(pet.getAge().getAge()).isEqualTo(10);
        assertThat(pet.getPetGender()).isEqualTo(PetGender.MAN);
        assertThat(pet.getSizeType()).isEqualTo(SizeType.LARGE);
        assertThat(pet.getBreed()).isEqualTo("리트리버");
        assertThat(pet.getName()).isEqualTo("댕댕이");
        assertThat(pet.isNeutering()).isTrue();
    }

    @Test
    void 펫_정보_업데이트() {
        //given
        Pet pet = createPet();
        //when
        Pet updatedPet = updatedPet();
        pet.update(updatedPet);
        //then
        assertThat(pet).isNotNull();
        assertThat(pet.getId()).isNull();
        assertThat(pet.getName()).isEqualTo("댕댕걸");
        assertThat(pet.getAge()).isNotNull();
        assertThat(pet.getAge().getAge()).isEqualTo(10);
        assertThat(pet.getAge().getBirthMonth()).isEqualTo(4);
        assertThat(pet.getAge().getBirthYear()).isEqualTo(2023);
        assertThat(pet.getPetGender()).isEqualTo(PetGender.WOMAN);
        assertThat(pet.getPetGender().getDescription()).isEqualTo("암컷");
        assertThat(pet.getSizeType()).isEqualTo(SizeType.SMALL);
        assertThat(pet.getSizeType().getDescription()).isEqualTo("소형견");
        assertThat(pet.isNeutering()).isFalse();
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
                .age(age)
                .selfIntroduction("최고의 개발자")
                .build();
    }

    private Pet createPet() {
        return Pet.builder()
                .user(this.user)
                .age(this.age)
                .petGender(PetGender.MAN)
                .sizeType(SizeType.LARGE)
                .breed("리트리버")
                .name("댕댕이")
                .neutering(true)
                .build();
    }

    private Pet updatedPet() {
        return Pet.builder()
                .name("댕댕걸")
                .age(this.age)
                .breed("샴")
                .petGender(PetGender.WOMAN)
                .sizeType(SizeType.SMALL)
                .neutering(false)
                .build();
    }
}