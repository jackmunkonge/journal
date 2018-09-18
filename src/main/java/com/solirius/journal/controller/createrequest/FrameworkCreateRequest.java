package com.solirius.journal.controller.createrequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class FrameworkCreateRequest {
    @JsonProperty("libraryName")
    private String libraryName;

    @JsonProperty("name")
    @NotNull(message = "Name must be defined")
    private String name;

    @JsonProperty("description")
    private String description;

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
