package com.solirius.journal.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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


    // RESOURCE MODEL
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "principles")
    @JsonIgnoreProperties({"frameworks, languages, libraries, plugins, principles, tools"})
    private List<Resource> resources = new ArrayList<>();


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

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}

