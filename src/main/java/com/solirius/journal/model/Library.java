package com.solirius.journal.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "library")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "library_id")
    private Integer libraryId;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "description")
    private String description;


    // PROJECT MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "libraries")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Project> projects = new ArrayList<>();

    // RESOURCE MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "libraries")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Resource> resources = new ArrayList<>();

    // LANGUAGE MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "libraries")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Language> languages = new ArrayList<>();

    // FRAMEWORK MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "libraries")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Framework> frameworks = new ArrayList<>();


    // TOOL MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "library_tool",
            joinColumns = { @JoinColumn(name="library_id") },
            inverseJoinColumns = { @JoinColumn(name="tool_id") }
    )
    @Column(name = "tool", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Tool> tools = new ArrayList<>();


    //GETTERS AND SETTERS
    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
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

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }
}

