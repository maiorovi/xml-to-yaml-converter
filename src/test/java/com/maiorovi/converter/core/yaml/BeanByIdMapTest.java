package com.maiorovi.converter.core.yaml;

import com.maiorovi.converter.core.domain.XmlNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BeanByIdMapTest {

    private BeanByIdMap beanByIdMap;



    @BeforeEach
    void setUp() {
        XmlNode beansNode = new XmlNode("beans");

        XmlNode beanNode = new XmlNode("bean");
        beanNode.addAttribute("id", "helloWorld");
        beanNode.addAttribute("class", "com.tutorialspoint.HelloWorld");

        XmlNode msgPropertyNode = new XmlNode("property");
        msgPropertyNode.addAttribute("name", "message");
        msgPropertyNode.addAttribute("value", "Hello World");

        XmlNode errMessagePropertyNode = new XmlNode("property");
        errMessagePropertyNode.addAttribute("name", "errMessage");
        errMessagePropertyNode.addAttribute("ref", "errMessageBean");

        beanNode.addChild(msgPropertyNode);
        beanNode.addChild(errMessagePropertyNode);

        XmlNode errMessageBean = new XmlNode("bean");
        errMessageBean.addAttribute("id", "errMessageBean");
        errMessageBean.addAttribute("class", "com.tutorial.ErrMessage");

        XmlNode errMsgProp = new XmlNode("property");
        errMsgProp.addAttribute("name", "message");
        errMsgProp.addAttribute("value", "Hello World");


        errMessageBean.addChild(errMsgProp);

        beansNode.addChild(errMessageBean);
        beansNode.addChild(beanNode);

        beanByIdMap = new BeanByIdMap(beansNode);
    }

    @Test
    void lookupsBeanById() {
        XmlNode errMessageBean = beanByIdMap.getBeanById("errMessageBean");

        assertThat(errMessageBean).isNotNull();
        assertThat(errMessageBean.getAttributeByName("id").getValue()).isEqualTo("errMessageBean");
    }
}