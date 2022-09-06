package com.example.roomate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPostResponseDto {
    private Long id;
    private String title;
    private String member;
    private Long heartNum;
}
