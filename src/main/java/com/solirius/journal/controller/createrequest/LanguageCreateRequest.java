package com.solirius.journal.controller.createrequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class LanguageCreateRequest {
    @JsonProperty("name")
    @NotNull(message = "Name must be defined")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("libraryName")
    private String libraryName;

    @JsonProperty("frameworkName")
    private String frameworkName;

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

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getFrameworkName() {
        return frameworkName;
    }

    public void setFrameworkName(String frameworkName) {
        this.frameworkName = frameworkName;
    }
}