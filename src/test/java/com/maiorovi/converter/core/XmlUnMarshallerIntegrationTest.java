package com.maiorovi.converter.core;

import com.maiorovi.converter.core.domain.XmlAttribute;
import com.maiorovi.converter.core.domain.XmlNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class XmlUnMarshallerIntegrationTest {

    XmlUnMarshaller xmlUnMarshaller;

    @BeforeEach
    void setUp() {
        xmlUnMarshaller = new XmlUnMarshaller();
    }

    @Test
    void unmarshallDefaultXml() {
        XmlNode root = xmlUnMarshaller.unmarshall("classpath:small-emp.xml");

        XmlNode expectedEmployeeNode = new XmlNode("Employee");
        expectedEmployeeNode.addAttribute(new XmlAttribute("id", "1"));
        expectedEmployeeNode.addChild(new XmlNode("age", "25"));
        expectedEmployeeNode.addChild(new XmlNode("name", "Adrash"));

        assertThat(root.getId()).isEqualTo("Employees");
        assertThat(root.getChildren()).hasSize(1);
        assertThat(root.getChildren())
                .containsExactly(expectedEmployeeNode);

    }
}