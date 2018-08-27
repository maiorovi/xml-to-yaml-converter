package com.maiorovi.converter.core.domain.view;

import com.maiorovi.converter.core.domain.XmlAttribute;

import java.util.ArrayList;
import java.util.List;

public class StartElementView {
    private String id;
    private List<XmlAttribute> xmlAttributes;

    public StartElementView(String id) {
        this.id = id;
        this.xmlAttributes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<XmlAttribute> getXmlAttributes() {
        return new ArrayList<>(xmlAttributes);
    }

    public void addAttribute(XmlAttribute xmlAttribute) {
        xmlAttributes.add(xmlAttribute);
    }
    public void addAttribute(String id, String value) {
        xmlAttributes.add(new XmlAttribute(id, value));
    }

    public void addAttributes(List<XmlAttribute> attributes) {
        xmlAttributes.addAll(attributes);
    }

}
