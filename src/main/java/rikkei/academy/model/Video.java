package rikkei.academy.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime uploadDate;
    @Lob
    private String videoUrl;
    @ManyToMany
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("videos")
    private List<Category> category;
    @ManyToOne
    @JoinColumn(name = "channel_id")
    @JsonIgnoreProperties("videos")
    private Channel channel;
    @OneToMany(mappedBy = "video")
    @JsonIgnore
    private List<Like> likes;

    @OneToMany(mappedBy = "video")
    @JsonIgnoreProperties("video")
    private List<Comment> comments;

    private int views;
//    @Column(columnDefinition = "BIT DEFAULT true")
    private boolean status = true;


}
