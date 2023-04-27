package moment.hong.component.pet.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moment.hong.component.pet.domain.enumeration.PetGender;
import moment.hong.component.pet.domain.enumeration.SizeType;
import moment.hong.component.user.domain.User;
import moment.hong.core.common.BaseEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Pet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    private Age age;

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_gender")
    private PetGender petGender;

    @Enumerated(EnumType.STRING)
    @Column(name = "size_type")
    private SizeType sizeType;

    @Column(nullable = false)
    private String breed;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean neutering;

    @Builder
    public Pet(User user, Age age, PetGender petGender, SizeType sizeType, String breed, String name,
               boolean neutering) {
        this.user = user;
        this.age = age;
        this.petGender = petGender;
        this.sizeType = sizeType;
        this.breed = breed;
        this.name = name;
        this.neutering = neutering;
    }

    public void update(Pet updatedPet) {
        this.name = updatedPet.getName();
        this.age = updatedPet.getAge();
        this.breed = updatedPet.getBreed();
        this.petGender = updatedPet.getPetGender();
        this.sizeType = updatedPet.getSizeType();
        this.neutering = updatedPet.isNeutering();
    }
}