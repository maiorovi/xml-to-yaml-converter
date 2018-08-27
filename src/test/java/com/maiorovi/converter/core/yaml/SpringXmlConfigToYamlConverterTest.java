package com.maiorovi.converter.core.yaml;

import com.maiorovi.converter.core.domain.XmlNode;
import com.maiorovi.converter.core.yaml.configuration.YamlConverterConfiguration;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SpringXmlConfigToYamlConverterTest {

    private static final String BASE_DIR = "expected/yaml/";

    SpringXmlConfigToYamlConverter springXmlConfigToYamlConverter;


    @BeforeEach
    void setUp() {
        YamlConverterConfiguration yamlConverterConfiguration = new YamlConverterConfiguration();
        yamlConverterConfiguration.addClass("HelloWorld");
        yamlConverterConfiguration.addClass("SomeClass");

        springXmlConfigToYamlConverter = new SpringXmlConfigToYamlConverter(yamlConverterConfiguration);

    }

    @Test
    void convertsSimpleBeanNodeToYaml() {
        XmlNode beansNode = new XmlNode("beans");

        XmlNode beanNode = new XmlNode("bean");

        beanNode.addAttribute("id", "helloWorld");
        beanNode.addAttribute("class", "com.tutorialspoint.HelloWorld");

        XmlNode propertyNode = new XmlNode("message");
        propertyNode.addAttribute("name", "message");
        propertyNode.addAttribute("value", "Hello World");

        beanNode.addChild(propertyNode);


        beansNode.addChild(beanNode);

        String yaml = springXmlConfigToYamlConverter.convert(beansNode);

        assertThat(yaml).isEqualTo("id: helloWorld\nmessage: Hello World");
    }

    @Test
    void convertsBeanWithFewSimplePropertiesToYaml() {
        XmlNode beansNode = new XmlNode("beans");

        XmlNode beanNode = new XmlNode("bean");

        beanNode.addAttribute("id", "helloWorld");
        beanNode.addAttribute("class", "com.tutorialspoint.HelloWorld");

        XmlNode msgPropertyNode = new XmlNode("property");
        msgPropertyNode.addAttribute("name", "message");
        msgPropertyNode.addAttribute("value", "Hello World");

        XmlNode errMessagePropertyNode = new XmlNode("property");
        errMessagePropertyNode.addAttribute("name", "errMessage");
        errMessagePropertyNode.addAttribute("value", "Error happened");

        beanNode.addChild(msgPropertyNode);
        beanNode.addChild(errMessagePropertyNode);

        beansNode.addChild(beanNode);

        String yaml = springXmlConfigToYamlConverter.convert(beansNode);

        assertThat(yaml).isEqualTo("id: helloWorld\nmessage: Hello World\nerrMessage: Error happened");
    }

    @Test
    void convertsBeanWithPropertyWhichReferencesAnotherBean() {
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
        errMsgProp.addAttribute("name", "errMessageInBean");
        errMsgProp.addAttribute("value", "error message goes here");

        errMessageBean.addChild(errMsgProp);


        XmlNode errMsgProp1 = new XmlNode("property");
        errMsgProp1.addAttribute("name", "reportMessage");
        errMsgProp1.addAttribute("value", "this is report message");

        errMessageBean.addChild(errMsgProp1);

        beansNode.addChild(errMessageBean);
        beansNode.addChild(beanNode);

        String yaml = springXmlConfigToYamlConverter.convert(beansNode);

        assertThat(yaml).isEqualTo(getContent(BASE_DIR+"property-ref.yaml"));
    }

    @Test
    void convertsBeanWithInnerBean() {
        XmlNode beansNode = new XmlNode("beans");

        XmlNode beanNode = new XmlNode("bean");
        beanNode.addAttribute("id", "helloWorld");
        beanNode.addAttribute("class", "com.tutorialspoint.HelloWorld");

        XmlNode msgPropertyNode = new XmlNode("property");
        msgPropertyNode.addAttribute("name", "message");
        msgPropertyNode.addAttribute("value", "Hello World");

        XmlNode errMessagePropertyNode = new XmlNode("property");
        errMessagePropertyNode.addAttribute("name", "errMessage");


        beanNode.addChild(msgPropertyNode);
        beanNode.addChild(errMessagePropertyNode);
        //inner bean
        XmlNode errMessageBean = new XmlNode("bean");
        errMessageBean.addAttribute("class", "com.tutorial.ErrMessage");

        XmlNode errMsgProp = new XmlNode("property");
        errMsgProp.addAttribute("name", "errMessageInBean");
        errMsgProp.addAttribute("value", "error message goes here");

        errMessageBean.addChild(errMsgProp);

        XmlNode errMsgProp1 = new XmlNode("property");
        errMsgProp1.addAttribute("name", "reportMessage");
        errMsgProp1.addAttribute("value", "this is report messag");

        errMessageBean.addChild(errMsgProp1);

        errMessagePropertyNode.addChild(errMessageBean);
        beansNode.addChild(beanNode);

        String yaml = springXmlConfigToYamlConverter.convert(beansNode);

        assertThat(yaml).isEqualTo(getContent(BASE_DIR+"property-ref.yaml"));
    }

    @Test
    void convertsBeanWithListAndRef() {
        XmlNode beansNode = new XmlNode("beans");

        XmlNode beanNode = new XmlNode("bean");
        beanNode.addAttribute("id", "someBean");
        beanNode.addAttribute("class", "com.somePackage.SomeClass");

        //list
        XmlNode propertyList = new XmlNode("list");
        propertyList.addAttribute("name", "myList");

        XmlNode ref1 = new XmlNode("ref");
        ref1.addAttribute("bean", "someBeanInTheList");

        XmlNode ref2 = new XmlNode("ref");
        ref2.addAttribute("bean", "someOtherBeanInTheList");

        propertyList.addChild(ref1);
        propertyList.addChild(ref2);


        XmlNode prop = new XmlNode("property");
        prop.addAttribute("name",  "myList");
        prop.addChild(propertyList);

        beanNode.addChild(prop);

        //bean1
        XmlNode someBeanInTheList = new XmlNode("bean");
        someBeanInTheList.addAttribute("id", "someBeanInTheList");
        someBeanInTheList.addAttribute("class", "someBeanInTheList");
        XmlNode property = new XmlNode("property");
        property.addAttribute("name", "prop1Msg");
        property.addAttribute("value", "this is prop1");

        XmlNode property2 = new XmlNode("property");
        property2.addAttribute("name", "prop2Msg");
        property2.addAttribute("value", "this is prop2");

        someBeanInTheList.addChild(property);
        someBeanInTheList.addChild(property2);

        //bean2
        XmlNode someOtherBeanInTheList = new XmlNode("bean");
        someOtherBeanInTheList.addAttribute("id", "someOtherBeanInTheList");
        someOtherBeanInTheList.addAttribute("class", "SomeOtherClass");

        XmlNode property3 = new XmlNode("property");
        property3.addAttribute("name", "prop3Msg");
        property3.addAttribute("value", "this is prop3");

        XmlNode property4 = new XmlNode("property");
        property4.addAttribute("name", "prop4Msg");
        property4.addAttribute("value", "this is prop4");

        someOtherBeanInTheList.addChild(property3);
        someOtherBeanInTheList.addChild(property4);

        beansNode.addChild(beanNode);
        beansNode.addChild(someBeanInTheList);
        beansNode.addChild(someOtherBeanInTheList);

        String yaml = springXmlConfigToYamlConverter.convert(beansNode);

        assertThat(yaml).isEqualTo(getContent(BASE_DIR+"property-list.yaml"));
    }

    private String getContent(String classpathFile) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(classpathFile);
        try {
            return IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}