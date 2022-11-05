package com.example.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DrawingDto {
    List<LineDto> lines = new ArrayList<>();
}
