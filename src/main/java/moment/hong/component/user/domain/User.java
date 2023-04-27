package moment.hong.component.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moment.hong.component.pet.domain.Age;
import moment.hong.component.user.domain.enumeration.Gender;
import moment.hong.component.user.domain.enumeration.UserRole;
import moment.hong.core.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.GUEST;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    @Embedded
    private Address address;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Column(name = "email")
    private String email;

    @Embedded
    private Age age;

    @Column(name = "self_introduction", length = 100)
    private String selfIntroduction;

    @Builder
    public User(UserRole userRole, Gender gender, String userName, String password, Address address,
                String nickname, String email, Age age, String selfIntroduction) {
        this.userRole = userRole;
        this.gender = gender;
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.nickname = nickname;
        this.email = email;
        this.age = age;
        this.selfIntroduction = selfIntroduction;
    }

    public void signUp(User user) {
        this.gender = user.getGender();
        this.address = user.getAddress();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.selfIntroduction = user.getSelfIntroduction();
    }
}