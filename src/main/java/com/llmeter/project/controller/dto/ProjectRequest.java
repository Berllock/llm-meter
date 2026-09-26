package com.llmeter.project.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequest(
        @NotBlank String name) {

}
