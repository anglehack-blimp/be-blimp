package com.blimp.backend.dto;

public record BlimpResponse<T>(T data, String errors) {

    public BlimpResponse(T data) {
        this(data, null);
    }

}
