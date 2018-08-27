package com.maiorovi.converter.core.domain;

import java.util.Map;
import java.util.Objects;

public class XmlAttribute {

    private final String id;
    private final String value;

    public XmlAttribute(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "XmlAttribute{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XmlAttribute)) return false;
        XmlAttribute that = (XmlAttribute) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }
}
