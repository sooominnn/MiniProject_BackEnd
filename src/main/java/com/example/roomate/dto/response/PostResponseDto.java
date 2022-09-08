package com.example.roomate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
//    private String member;
//    private String content;
    private Long heartNum;
    private boolean heartOn;
//    private String imageUrl;
//    private List<HeartResponseDto> hearts;
    private List<CommentResponseDto> comments;
}
