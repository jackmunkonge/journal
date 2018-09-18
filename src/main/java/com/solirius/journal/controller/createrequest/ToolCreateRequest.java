package com.solirius.journal.controller.createrequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class ToolCreateRequest {
    @JsonProperty("name")
    @NotNull(message = "Name must be defined")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("pluginName")
    private String pluginName;

    @JsonProperty("languageName")
    private String languageName;

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

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getFrameworkName() {
        return frameworkName;
    }

    public void setFrameworkName(String frameworkName) {
        this.frameworkName = frameworkName;
    }
}