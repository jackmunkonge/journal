package com.solirius.journal.domain;

import javax.persistence.*;

@Entity
@Table(name = "resource")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "resource_id")
    private Integer resourceId;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name="framework_id", nullable=true)
    private Framework framework;

    @ManyToOne
    @JoinColumn(name="language_id", nullable=false)
    private Language language;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Framework getFramework() { return framework; }

    public void setFramework(Framework framework) { this.framework = framework; }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
