package com.flb.ignite.cassandra.model;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;


public class CatalogCategory implements Serializable {

    @QuerySqlField private long id;
    @QuerySqlField private Long parentId;
    @QuerySqlField private String name;
    @QuerySqlField private String description;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    // public getters and setters
}
