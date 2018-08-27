package com.maiorovi.converter.core;

import com.maiorovi.converter.core.domain.XmlNode;
import com.maiorovi.converter.core.yaml.SpringXmlConfigToYamlConverter;
import com.maiorovi.converter.core.yaml.configuration.YamlConverterConfiguration;
import org.junit.jupiter.api.Test;

public class ConverterIntegrationTest {

    @Test
    void name() {
        YamlConverterConfiguration yamlConverterConfiguration = new YamlConverterConfiguration();
        yamlConverterConfiguration.addClass("BusinessRule");
        SpringXmlConfigToYamlConverter springXmlConfigToYamlConverter = new SpringXmlConfigToYamlConverter(yamlConverterConfiguration);
        XmlUnMarshaller xmlUnMarshaller = new XmlUnMarshaller();

        XmlNode root = xmlUnMarshaller.unmarshall("classpath:test.xml");
        String convert = springXmlConfigToYamlConverter.convert(root);

        System.out.println(convert);
    }
}
