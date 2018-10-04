package com.solirius.journal.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
@Table(name = "tag")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tag_type", discriminatorType = DiscriminatorType.STRING)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private Integer tagId;

    @Column(
            name = "tag_name",
            unique = true,
            nullable = false
    )
    private String tagName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "tag_description")
    private String tagDescription;


    // GETTERS AND SETTERS
    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }
}
