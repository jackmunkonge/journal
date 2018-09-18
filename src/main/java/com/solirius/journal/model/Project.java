package com.solirius.journal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;


    // RESOURCE MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_resource",
            joinColumns = { @JoinColumn(name="project_id") },
            inverseJoinColumns = { @JoinColumn(name="resource_id") }
    )
    @Column(name = "resource", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Resource> resources = new ArrayList<>();


    // TOOL MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_tool",
            joinColumns = { @JoinColumn(name="project_id") },
            inverseJoinColumns = { @JoinColumn(name="tool_id") }
    )
    @Column(name = "tool", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Tool> tools = new ArrayList<>();


    // LIBRARY MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_library",
            joinColumns = { @JoinColumn(name="project_id") },
            inverseJoinColumns = { @JoinColumn(name="library_id") }
    )
    @Column(name = "library", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Library> libraries = new ArrayList<>();


    // PRINCIPLE MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_principle",
            joinColumns = { @JoinColumn(name="project_id") },
            inverseJoinColumns = { @JoinColumn(name="principle_id") }
    )
    @Column(name = "principle", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Principle> principles = new ArrayList<>();


    // LANGUAGE MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_language",
            joinColumns = { @JoinColumn(name="project_id") },
            inverseJoinColumns = { @JoinColumn(name="language_id") }
    )
    @Column(name = "language", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Language> languages = new ArrayList<>();


    // FRAMEWORK MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_framework",
            joinColumns = { @JoinColumn(name="project_id") },
            inverseJoinColumns = { @JoinColumn(name="framework_id") }
    )
    @Column(name = "framework", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Framework> frameworks = new ArrayList<>();


    // GETTERS AND SETTERS
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
