package com.solirius.journal.domain;

import javax.persistence.*;

@Entity
@Table(name = "framework")
public class Framework {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "framework_id")
    private Integer frameworkId;

    @Column(name = "name",unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable=false)
    private Language language;

    public Integer getFrameworkId() {
        return frameworkId;
    }

    public void setFrameworkId(Integer frameworkId) {
        this.frameworkId = frameworkId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
