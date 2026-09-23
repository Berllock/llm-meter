package com.llmeter.project;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequest(
        @NotBlank String name) {

}
