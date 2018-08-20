package com.solirius.journal.controller.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class FrameworkCreateRequest {
    @JsonProperty("languageName")
    @NotNull(message = "Name must be defined")
    private String languageName;

    @JsonProperty("name")
    @NotNull(message = "Name must be defined")
    private String name;

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
