package com.example.draw.encoder;

import com.example.dto.LineDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class LineEncoder implements Encoder.Text<LineDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String encode(LineDto lineDto) throws EncodeException {
        try {
            return objectMapper.writeValueAsString(lineDto);
        } catch (JsonProcessingException e) {
            throw new EncodeException(lineDto, e.getMessage());
        }
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
