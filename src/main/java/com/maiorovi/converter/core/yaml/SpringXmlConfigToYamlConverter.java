package com.maiorovi.converter.core.yaml;

import com.maiorovi.converter.core.domain.XmlAttribute;
import com.maiorovi.converter.core.domain.XmlNode;
import com.maiorovi.converter.core.yaml.configuration.YamlConverterConfiguration;

import java.util.List;
import java.util.stream.Collectors;

public class SpringXmlConfigToYamlConverter {

    private BeanByIdMap beanByIdMap;
    private YamlConverterConfiguration yamlConverterConfiguration;

    public SpringXmlConfigToYamlConverter(YamlConverterConfiguration yamlConverterConfiguration) {
        this.yamlConverterConfiguration = yamlConverterConfiguration;
    }

    public String convert(XmlNode root) {
        beanByIdMap = new BeanByIdMap(root);

        List<XmlNode> children = root.getChildren();
        StringBuffer sb = new StringBuffer();

        for(XmlNode child : children) {
            XmlAttribute aClass = child.getAttributeByName("class");
            if (aClass != null && yamlConverterConfiguration.containsClass(aClass.getValue())) {
                XmlAttribute attributeByName = child.getAttributeByName("id");

                sb.append("id");
                sb.append(": ");
                sb.append(attributeByName.getValue());
                sb.append("\n");
                sb.append(processProperties(child, ""));
                sb.append("\n");
            }
        }

        return sb.substring(0, sb.length() - 1);
    }

    private String processProperties(XmlNode beanNode, String indentation) {
        StringBuffer sb = new StringBuffer();

        for (XmlNode propertyNode : beanNode.getChildren()) {
            if (isList(propertyNode)) {
                sb.append(processList(propertyNode, "  "));
                continue;
            }

            XmlAttribute name = propertyNode.getAttributeByName("name");
            XmlAttribute value = getValueAttribute(propertyNode);

            //inner bean case
            if(value == null) {
                sb.append(name.getValue());
                sb.append(":\n");
                sb.append(processInnerBean(propertyNode, "  "));
                continue;
            }

            //is ref
            if ("ref".equals(value.getId())) {
                XmlNode beanById = beanByIdMap.getBeanById(value.getValue());
                sb.append(name.getValue());
                sb.append(":\n");
                sb.append(processProperties(beanById, "  "));
                continue;
            }
            sb.append(indentation);
            sb.append(processProperty(name, value));
        }

        return sb.substring(0, sb.length()-1);
    }

    private String processInnerBean(XmlNode innerBean, String indentation) {
        return innerBean.getChildren().stream()
                .flatMap(ch -> ch.getChildren().stream())
                .map(prop -> indentation + processProperty(prop.getAttributeByName("name"), getValueAttribute(prop)))
                .collect(Collectors.joining());
    }

    private String processList(XmlNode listNode, String indentation) {
        XmlAttribute c = listNode.getAttributeByName("name");
        StringBuilder builder = new StringBuilder();
        builder.append(c.getValue());
        builder.append(":");

        String l = listNode.getChildren().stream()
                .flatMap(ch -> ch.getChildren().stream())
                .map(ch -> {
                    StringBuilder sb = new StringBuilder();
                    if ("ref".equals(ch.getId())) {
                        XmlAttribute attributeByName = ch.getAttributeByName("bean");
                        XmlNode beanById = beanByIdMap.getBeanById(attributeByName.getValue());
                        sb.append(indentation);
                        sb.append("- id: ");
                        sb.append(attributeByName.getValue());
                        sb.append("\n");
                        sb.append(processProperties(beanById, indentation+indentation));
                    }

                    return sb.toString();
                }).collect(Collectors.joining("\n"));

        return builder.append("\n").append(l).toString();
    }

    private XmlAttribute getValueAttribute(XmlNode propertyNode) {
        XmlAttribute value = propertyNode.getAttributeByName("value");
        if (value != null) {
            return value;
        }

        return propertyNode.getAttributeByName("ref");
    }


    private String processProperty(XmlAttribute nameAttr, XmlAttribute valueAttr) {
        return nameAttr.getValue() + ": " + valueAttr.getValue() +"\n";
    }

    private boolean isList(XmlNode xmlNode) {
        List<XmlNode> children = xmlNode.getChildren();

        return children.size() == 1 && "list".equals(children.get(0).getId());
    }

    private boolean isInner() {
        return true;
    }
}
