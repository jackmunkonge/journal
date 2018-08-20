package com.solirius.journal.controller.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class ResourceCreateRequest {
    @JsonProperty("frameworkName")
    @NotNull(message = "Name must be defined")
    private String frameworkName;

    @JsonProperty("languageName")
    @NotNull(message = "Name must be defined")
    private String languageName;

    @JsonProperty("name")
    @NotNull(message = "Name must be defined")
    private String name;

    @JsonProperty("url")
    @NotNull(message = "Name must be defined")
    private String url;

    public String getFrameworkName() {
        return frameworkName;
    }

    public void setFrameworkName(String frameworkName) {
        this.frameworkName = frameworkName;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
