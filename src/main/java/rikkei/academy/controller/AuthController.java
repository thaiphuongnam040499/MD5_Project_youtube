package rikkei.academy.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.PasswordDTO;
import rikkei.academy.dto.request.SigninForm;
import rikkei.academy.dto.request.SignupForm;
import rikkei.academy.dto.response.JwtResponse;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.Channel;
import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.User;
import rikkei.academy.security.jwt.JwtProvider;
import rikkei.academy.security.userPrincipal.UserPrincipal;
import rikkei.academy.service.RoleService.IRoleService;
import rikkei.academy.service.channelService.IChannelService;
import rikkei.academy.service.userService.IUserService;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/v6/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final IChannelService channelService;

    @PostMapping("/signUp")
    public ResponseEntity<ResponseMessage> doLogin(@RequestBody SignupForm signupForm) {
        boolean isExitsUserName = userService.existsByUsername(signupForm.getUsername());
        if (isExitsUserName) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("Failed to login")
                            .message("Username already exists")
                            .data("")
                            .build()
            );
        }
        boolean isExitsEmail = userService.existsByEmail(signupForm.getEmail());
        if (isExitsEmail) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("Failed to login")
                            .message("Email already exists")
                            .data("")
                            .build()
            );
        }
        Set<Role> roles = new HashSet<>();
        if (signupForm.getRoles() == null || signupForm.getRoles().isEmpty()) {
            Role role = roleService.findByName(RoleName.USER)
                    .orElseThrow(() -> new RuntimeException("Failed not found role"));
            roles.add(role);
        } else {
            signupForm.getRoles().forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByName(RoleName.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Failed not found role"));
                        roles.add(adminRole);
                    case "pm":
                        Role pmRole = roleService.findByName(RoleName.PM)
                                .orElseThrow(() -> new RuntimeException("Failed not found role"));
                        roles.add(pmRole);
                    case "user":
                        Role userRole = roleService.findByName(RoleName.USER)
                                .orElseThrow(() -> new RuntimeException("Failed not found role"));
                        roles.add(userRole);
                }
            });
        }
        User user = User.builder()
                .username(signupForm.getUsername())
                .fullname(signupForm.getFullname())
                .email(signupForm.getEmail())
                .password(passwordEncoder.encode(signupForm.getPassword()))
                .phone(signupForm.getPhone())
                .roles(roles)
                .build();

        User tempUser = userService.save(user);
        Channel channel = Channel.builder().owner(tempUser).build();
        channel.setName(tempUser.getUsername());
        channelService.save(channel);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("Success")
                        .message("Account created successfully ")
                        .data(userService.findById(tempUser.getId()))
                        .build()
        );
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> doLogin(@RequestBody SigninForm signinForm, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            signinForm.getUsername(),
                            signinForm.getPassword()
                    ));
            String token = jwtProvider.generateToken(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return new ResponseEntity<>(
                    JwtResponse.builder()
                            .status("Success")
                            .type("Bearer")
                            .fullname(userPrincipal.getFullname())
                            .token(token)
                            .roles(userPrincipal.getAuthorities())
                            .build(), HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(
                    ResponseMessage.builder()
                            .status("Failed")
                            .message("Invalid username or password")
                            .data("")
                            .build(), HttpStatus.UNAUTHORIZED
            );
        }
    }

    @PutMapping("/changePass/{id}")
    public ResponseEntity<ResponseMessage> changePassword(@PathVariable Long id,
                                                          @RequestBody PasswordDTO passwordDTO) {
        Optional<User> usersOptional = Optional.ofNullable(userService.findById(id));
        if (passwordEncoder.matches(passwordDTO.getOldPassword(), usersOptional.get().getPassword())) {
            if (passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())) {
                usersOptional.get().setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
                userService.save(usersOptional.get());
                return ResponseEntity.ok().body(
                        ResponseMessage.builder()
                                .status("OK")
                                .message("Thay đổi mật khẩu thành công!")
                                .data("")
                                .build()
                );
            }
        }
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("False")
                        .message("Thay đổi mật khẩu thất bại!")
                        .data("")
                        .build()
        );
    }

    @PostMapping("/logout")
    public String logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        if (auth != null) {
            SecurityContextHolder.clearContext();
        }
        return "Đăng xuất thành công";
    }
}
