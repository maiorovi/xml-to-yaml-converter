package com.maiorovi.converter.core.yaml;

import com.maiorovi.converter.core.domain.XmlAttribute;
import com.maiorovi.converter.core.domain.XmlNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanByIdMap {
    private Map<String, XmlNode> beanById;

    public BeanByIdMap(XmlNode root) {
        beanById = initMap(root, new HashMap<>());
    }

    private Map<String, XmlNode> initMap(XmlNode root, Map<String, XmlNode> acc) {
        List<XmlNode> children = root.getChildren();

        for(XmlNode child : children) {
            if("bean".equals(child.getId())) {
                XmlAttribute idAttribute = child.getAttributeByName("id");
                acc.put(idAttribute.getValue(), child);

                initMap(child, acc);
            }
        }

        return acc;
    }


    public XmlNode getBeanById(String id) {
        return beanById.get(id);
    }
}
