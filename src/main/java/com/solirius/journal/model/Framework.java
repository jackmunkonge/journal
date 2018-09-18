package com.solirius.journal.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "framework")
public class Framework {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "framework_id")
    private Integer frameworkId;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "description")
    private String description;


    // PROJECT MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "frameworks")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Project> projects = new ArrayList<>();

    // RESOURCE MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "frameworks")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Resource> resources = new ArrayList<>();

    // TOOL MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "frameworks")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Tool> tools = new ArrayList<>();

    // PLUGIN MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "frameworks")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Plugin> plugins = new ArrayList<>();

    // LANGUAGE MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "frameworks")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Language> languages = new ArrayList<>();


    // LIBRARY MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "framework_library",
            joinColumns = { @JoinColumn(name="framework_id") },
            inverseJoinColumns = { @JoinColumn(name="library_id") }
    )
    @Column(name = "library", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Library> libraries = new ArrayList<>();


    // GETTERS AND SETTERS
    public Integer getFrameworkId() {
        return frameworkId;
    }

    public void setFrameworkId(Integer frameworkId) {
        this.frameworkId = frameworkId;
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

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }
}