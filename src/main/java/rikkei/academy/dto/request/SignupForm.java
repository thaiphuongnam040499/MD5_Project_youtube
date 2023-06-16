package rikkei.academy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rikkei.academy.model.Channel;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupForm {
    private String username;
    private String fullname;
    private String password;
    private String email;
    private String phone;
    private Set<String> roles;
}
