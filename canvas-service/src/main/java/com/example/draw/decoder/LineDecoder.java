package com.example.draw.decoder;

import com.example.dto.LineDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class LineDecoder implements Decoder.Text<LineDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public LineDto decode(String string) throws DecodeException {
        try {
            return objectMapper.readValue(string, LineDto.class);
        } catch (JsonProcessingException e) {
            throw new DecodeException(string, e.getMessage());
        }
    }

    @Override
    public boolean willDecode(String string) {
        try {
            objectMapper.readValue(string, LineDto.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
