package com.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class LineDto {
    private List<PointDto> line;
}
