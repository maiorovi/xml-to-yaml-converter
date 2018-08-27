package com.maiorovi.converter.core.handler;

import com.maiorovi.converter.core.domain.XmlAttribute;
import com.maiorovi.converter.core.domain.XmlNode;
import com.maiorovi.converter.core.domain.view.StartElementView;
import org.codehaus.stax2.XMLStreamReader2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StartElementEventHandlerTest {

    private StartElementEventHandler startElementEventHandler;
    private XMLStreamReader2 xmlStreamReader;

    @BeforeEach
    void setUp() {
        xmlStreamReader = mock(XMLStreamReader2.class);
        when(xmlStreamReader.getName()).thenReturn(new QName("Employee"));

        startElementEventHandler = new StartElementEventHandler();
    }

    @Test
    void handlesStartElementNameAsId() {
        XmlNode view = startElementEventHandler.handle(xmlStreamReader);

        assertThat(view.getId()).isEqualTo("Employee");
    }

    @Test
    void handlesAllAttributesOfStartingTag() {
        when(xmlStreamReader.getAttributeCount()).thenReturn(2);
        when(xmlStreamReader.getAttributeName(0)).thenReturn(new QName("id"));
        when(xmlStreamReader.getAttributeValue(0)).thenReturn("12345");
        when(xmlStreamReader.getAttributeName(1)).thenReturn(new QName("salary"));
        when(xmlStreamReader.getAttributeValue(1)).thenReturn("4500");

        XmlNode view = startElementEventHandler.handle(xmlStreamReader);

        assertThat(view.getXmlAttributes())
                .extracting(XmlAttribute::getId, XmlAttribute::getValue)
                .contains(tuple("id", "12345"), tuple("salary", "4500"));
    }
}