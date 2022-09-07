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
public class MainResponseDto {
    private List<PostResponseDto> posts;
    private List<PostResponseDto> heartedPosts;
}
