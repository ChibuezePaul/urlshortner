package com.isoft.code.urlshortner.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    Object data;
    @Builder.Default
    private String message = "Success";
}
