package com.solirius.journal.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tool")
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tool_id")
    private Integer toolId;

    @Column(name = "name", unique=true, nullable=false)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "description")
    private String description;


    // PROJECT MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tools")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Project> projects = new ArrayList<>();

    // RESOURCE MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tools")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Resource> resources = new ArrayList<>();

    // LIBRARY MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tools")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Library> libraries = new ArrayList<>();

    // PRINCIPLE MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tools")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Principle> principles = new ArrayList<>();


    // PLUGIN MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tool_plugin",
            joinColumns = { @JoinColumn(name="tool_id") },
            inverseJoinColumns = { @JoinColumn(name="plugin_id") }
    )
    @Column(name = "plugin", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Plugin> plugins = new ArrayList<>();


    // LANGUAGE MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tool_language",
            joinColumns = { @JoinColumn(name="tool_id") },
            inverseJoinColumns = { @JoinColumn(name="language_id") }
    )
    @Column(name = "language", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Language> languages = new ArrayList<>();


    // FRAMEWORK MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tool_framework",
            joinColumns = { @JoinColumn(name="tool_id") },
            inverseJoinColumns = { @JoinColumn(name="framework_id") }
    )
    @Column(name = "framework", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Framework> frameworks = new ArrayList<>();


    //GETTERS AND SETTERS
    public Integer getToolId() {
        return toolId;
    }

    public void setToolId(Integer toolId) {
        this.toolId = toolId;
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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }

    public List<Principle> getPrinciples() {
        return principles;
    }

    public void setPrinciples(List<Principle> principles) {
        this.principles = principles;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Framework> getFrameworks() {
        return frameworks;
    }

    public void setFrameworks(List<Framework> frameworks) {
        this.frameworks = frameworks;
    }
}

