package com.example.roomate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPostResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private Long heartNum;
    private boolean heartOn;
}
