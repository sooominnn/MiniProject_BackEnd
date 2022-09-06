package com.example.roomate.entity;

import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private Long heartNum;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

//    @Column(nullable = false)
//    private Integer comment_cnt = 0;

    //  @OneToOne(mappedBy = "post",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//  @JoinColumn(name="post_id", nullable = false)
    @Column
    private String image;

    @JoinColumn(name = "member", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostHeart> postHeart;

//    public void update(PostRequestDto postRequestDto, ImageResponseDto imageResponseDto) {
//        this.title = postRequestDto.getTitle();
//        this.content = postRequestDto.getContent();
//        this.image = imageResponseDto.getImageUrl();
//    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
//
//    public void comment_cnt_Up() {
//        this.comment_cnt++;
//    }
//
//    public void comment_cnt_Down() {
//        this.comment_cnt--;
//    }

}
