package moment.hong.component.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moment.hong.component.user.domain.enumeration.Gender;
import moment.hong.component.user.domain.enumeration.UserRole;
import moment.hong.core.common.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.GUEST;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Embedded
    private Address address;

    @Column(name = "nickname", nullable = false, length = 20, unique = true)
    private String nickname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(nullable = false, length = 4)
    private Integer age;

    @Column(name = "self_introduction", nullable = false, length = 100)
    private String selfIntroduction;

    @Builder
    public User(Long id, UserRole userRole, Gender gender, Address address, String nickname, String email, Integer age,
                String selfIntroduction) {
        this.id = id;
        this.userRole = userRole;
        this.gender = gender;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}