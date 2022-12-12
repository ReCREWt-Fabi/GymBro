package de.othr.im.gymbro.model;

import javax.persistence.*;

public class Filter {

    private String query = "";

    public Filter() {
    }

    public String getQuery() {
        return query.trim();
    }

    public void setQuery(String query) {
        this.query = query;
    }
}