package rikkei.academy.security.userPrincipal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rikkei.academy.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {
    private Long id;
    private String username;
    private String fullname;
    @JsonIgnore
    private String password;
    private String email;
    private String avatar;
    private String phone;
    private Collection<? extends GrantedAuthority> roles;
    public static UserPrincipal build(User user) {

        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return UserPrincipal.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullname(user.getFullname())
                .password(user.getPassword())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roles(grantedAuthorities)
                .build();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    // kiểm tra tài khoản có hết hạn không
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
//    tài khoản có bị block không
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // mã xác nhận có bị hết hạn không
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // tài khoản đã xác nhận chưa

    @Override
    public boolean isEnabled() {
        return true;
    }
}
