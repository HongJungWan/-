package moment.hong.component.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import moment.hong.component.user.domain.Address;
import moment.hong.component.user.domain.User;
import moment.hong.component.user.domain.enumeration.Gender;
import moment.hong.component.user.domain.enumeration.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
public class UserDto implements UserDetails {
    private Long id;
    private UserRole userRole;
    private Gender gender;
    private String userName;
    private String password;
    private Address address;
    private String nickname;
    private String email;
    private Integer age;
    private String selfIntroduction;

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getUserRole(),
                user.getGender(),
                user.getUserName(),
                user.getPassword(),
                user.getAddress(),
                user.getNickname(),
                user.getEmail(),
                user.getAge(),
                user.getSelfIntroduction()
                );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getUserRole().toString()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}