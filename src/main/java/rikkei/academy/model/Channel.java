package rikkei.academy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "channels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;
    private String avatarUrl;
    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @OneToMany(mappedBy = "channel")
    private List<Subscribe> subscribers;
    @OneToMany(mappedBy = "channel")
    @JsonIgnoreProperties("channel")
    private List<Video> videos;
    private int status = 0;
}
