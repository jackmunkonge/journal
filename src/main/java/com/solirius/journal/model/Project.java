package com.solirius.journal.model;

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

    @Column(name = "name", unique=true, nullable=false)
    private String name;

    @Column(name = "url")
    private String url;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "description")
    private String description;

    @Column(name = "file_path")
    private String filePath;


    // RESOURCE MODEL
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_resource",
            joinColumns = { @JoinColumn(name="project_id") },
            inverseJoinColumns = { @JoinColumn(name="resource_id") }
    )
    @Column(name = "resource", nullable=true)
    @JsonIgnoreProperties({"projects"})
    private List<Resource> resources = new ArrayList<>();


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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
