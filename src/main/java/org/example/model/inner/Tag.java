package org.example.model.inner;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Tag {

    public String name;
    public Set<Endpoint> endpoints;

    public Tag(String name) {
        this.name = name;
        this.endpoints = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
