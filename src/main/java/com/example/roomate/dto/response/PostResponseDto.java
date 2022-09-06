package com.example.roomate.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String member;
    private String content;
    private Long heartNum;
    private String imageUrl;
    private List<HeartResponseDto> hearts;
    private List<CommentResponseDto> comments;
}
