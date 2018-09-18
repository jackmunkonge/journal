package com.solirius.journal.controller.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class LanguageCreateRequest {
    @JsonProperty("name")
    @NotNull(message = "Language name must be defined")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
