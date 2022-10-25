package com.example.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DrawingDto {
    List<PointDto> points = new ArrayList<>();
}
