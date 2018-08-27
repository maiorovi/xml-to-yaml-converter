package com.maiorovi.converter.core.yaml.configuration;

import java.util.HashSet;
import java.util.Set;

public class YamlConverterConfiguration {
    private Set<String> classesToInclude;

    public YamlConverterConfiguration() {
        this.classesToInclude = new HashSet<>();
    }

    public void addClass(String className)  {
        classesToInclude.add(className);
    }

    public boolean containsClass(String className) {
        String[] parts = className.split("\\.");

        return classesToInclude.contains(parts[parts.length - 1]);
    }
}
