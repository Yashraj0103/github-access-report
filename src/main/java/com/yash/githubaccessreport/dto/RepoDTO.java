package com.yash.githubaccessreport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoDTO {

    private String name;

    public RepoDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}