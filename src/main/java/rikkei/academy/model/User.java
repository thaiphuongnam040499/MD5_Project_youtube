package rikkei.academy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String fullname;
    @JsonIgnore
    private String password;
    private String email;
    private String phone;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();
    @Lob
    private String avatar;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Subscribe> subscriptions;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comment> comments;
//    @OneToOne(mappedBy = "owner",cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Channel channel;
}
