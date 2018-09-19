package com.solirius.journal.model;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "principle")
public class Principle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "principle_id")
    private Integer principleId;

    @Column(name = "name",unique = true, nullable=false)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "description")
    private String description;


    // PROJECT MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "principles")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Project> projects = new ArrayList<>();

    // RESOURCE MODEL
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "principles")
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Resource> resources = new ArrayList<>();


    // TOOL MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "principle_tool",
            joinColumns = { @JoinColumn(name="principle_id") },
            inverseJoinColumns = { @JoinColumn(name="tool_id") }
    )
    @Column(name = "tool", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Tool> tools = new ArrayList<>();


    // LANGUAGE MODEL
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "principle_language",
            joinColumns = { @JoinColumn(name="principle_id") },
            inverseJoinColumns = { @JoinColumn(name="language_id") }
    )
    @Column(name = "language", nullable=true)
//    @JsonIgnoreProperties({"projects", "resources"})
    private List<Language> languages = new ArrayList<>();


    // GETTERS AND SETTERS
    public Integer getPrincipleId() {
        return principleId;
    }

    public void setPrincipleId(Integer principleId) {
        this.principleId = principleId;
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

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
}

