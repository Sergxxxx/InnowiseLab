package com.epam.winter_java_lab.task_13.domain;

public enum Role {
    USER, ADMIN;

    public String getAuthority() {
        return name();
    }
}



