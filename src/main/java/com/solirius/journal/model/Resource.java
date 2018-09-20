package com.solirius.journal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "resource")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "resource_id")
    private Integer resourceId;

    @Column(name = "name", unique=true, nullable=false)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "url")
    private String url;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "description")
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "file_path")
    private String filePath;


    // PROJECT MODEL
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources")
    @JsonIgnoreProperties({"resources"})
    private List<Project> projects = new ArrayList<>();


    // FRAMEWORK MODEL
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "resource_framework",
            joinColumns = { @JoinColumn(name="resource_id") },
            inverseJoinColumns = { @JoinColumn(name="framework_id") }
    )
    @Column(name = "framework", nullable=true)
    @JsonIgnoreProperties({"resources"})
    private List<Framework> frameworks = new ArrayList<>();


    // LANGUAGE MODEL
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "resource_language",
            joinColumns = { @JoinColumn(name="resource_id") },
            inverseJoinColumns = { @JoinColumn(name="language_id") }
    )
    @Column(name = "language", nullable=true)
    @JsonIgnoreProperties({"resources"})
    private List<Language> languages = new ArrayList<>();


    // LIBRARY MODEL
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "resource_library",
            joinColumns = { @JoinColumn(name="resource_id") },
            inverseJoinColumns = { @JoinColumn(name="library_id") }
    )
    @Column(name = "library", nullable=true)
    @JsonIgnoreProperties({"resources"})
    private List<Library> libraries = new ArrayList<>();


    // PLUGIN MODEL
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "resource_plugin",
            joinColumns = { @JoinColumn(name="resource_id") },
            inverseJoinColumns = { @JoinColumn(name="plugin_id") }
    )
    @Column(name = "plugin", nullable=true)
    @JsonIgnoreProperties({"resources"})
    private List<Plugin> plugins = new ArrayList<>();


    // PRINCIPLE MODEL
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "resource_principle",
            joinColumns = { @JoinColumn(name="resource_id") },
            inverseJoinColumns = { @JoinColumn(name="principle_id") }
    )
    @Column(name = "principle", nullable=true)
    @JsonIgnoreProperties({"resources"})
    private List<Principle> principles = new ArrayList<>();


    // TOOL MODEL
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "resource_tool",
            joinColumns = { @JoinColumn(name="resource_id") },
            inverseJoinColumns = { @JoinColumn(name="tool_id") }
    )
    @Column(name = "tool", nullable=true)
    @JsonIgnoreProperties({"resources"})
    private List<Tool> tools = new ArrayList<>();


    // GETTERS AND SETTERS
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Framework> getFrameworks() {
        return frameworks;
    }

    public void setFrameworks(List<Framework> frameworks) {
        this.frameworks = frameworks;
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

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }
}
