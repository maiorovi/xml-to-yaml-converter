package com.maiorovi.converter.core.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class XmlNode {
    private String id;
    private List<XmlAttribute> attributes;
    private String content;
    private List<XmlNode> children;
    private XmlNode parent;

    public XmlNode(String id) {
        this.id = id;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public XmlNode(String id, String content) {
        this.id = id;
        this.content = content;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public XmlNode(String id, List<XmlNode> children, XmlNode parent) {
        this.id = id;
        this.children = children;
        this.parent = parent;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public void setParent(XmlNode parent) {
        this.parent = parent;
    }

    public void addAttribute(String id, String value) {
        addAttribute(new XmlAttribute(id, value));
    }

    public  void addAttribute(XmlAttribute attribute) {
        attributes.add(attribute);
    }

    public void addChild(XmlNode xmlNode) {
        children.add(xmlNode);
    }

    public String getId() {
        return id;
    }

    public List<XmlAttribute> getAttributes() {
        return new ArrayList<>(attributes);
    }

    public List<XmlNode> getChildren() {
        return children;
    }

    public XmlAttribute getAttributeByName(String name) {
        return attributes.stream()
                .filter(xmlAttribute -> xmlAttribute.getId().equals(name))
                .findFirst()
                .orElse(null);
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "XmlNode{" +
                "id='" + id + '\'' +
                ", attributes=" + attributes +
                ", children=" + children +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XmlNode)) return false;
        XmlNode xmlNode = (XmlNode) o;
        return Objects.equals(id, xmlNode.id) &&
                Objects.equals(attributes, xmlNode.attributes) &&
                Objects.equals(children, xmlNode.children);
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attributes, children);
    }

    public void addAttributes(List<XmlAttribute> attributes) {
        this.attributes.addAll(attributes);
    }

    public List<XmlAttribute> getXmlAttributes() {
        return attributes;
    }

    public XmlNode getParent() {
        return parent;
    }
}
