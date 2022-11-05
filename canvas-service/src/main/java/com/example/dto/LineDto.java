package com.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class LineDto {
    private PointDto startingPoint;
    private PointDto endPoint;
}
