package com.solirius.journal.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "language_id")
    private Integer languageId;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "description")
    private String description;


    // PROJECT MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "languages")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Project> projects = new ArrayList<>();

    // RESOURCE MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "languages")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Resource> resources = new ArrayList<>();

    // TOOL MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "languages")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Tool> tools = new ArrayList<>();

    // PLUGIN MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "languages")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Plugin> plugins = new ArrayList<>();

    // PRINCIPLE MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "languages")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Principle> principles = new ArrayList<>();


    // LIBRARY MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "language_library",
            joinColumns = { @JoinColumn(name="language_id") },
            inverseJoinColumns = { @JoinColumn(name="library_id") }
    )
    @Column(name = "library", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Library> libraries = new ArrayList<>();


    // FRAMEWORK MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "language_framework",
            joinColumns = { @JoinColumn(name="language_id") },
            inverseJoinColumns = { @JoinColumn(name="framework_id") }
    )
    @Column(name = "framework", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Framework> frameworks = new ArrayList<>();


    // GETTERS AND SETTERS
    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
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

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public List<Principle> getPrinciples() {
        return principles;
    }

    public void setPrinciples(List<Principle> principles) {
        this.principles = principles;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }

    public List<Framework> getFrameworks() {
        return frameworks;
    }

    public void setFrameworks(List<Framework> frameworks) {
        this.frameworks = frameworks;
    }
}

