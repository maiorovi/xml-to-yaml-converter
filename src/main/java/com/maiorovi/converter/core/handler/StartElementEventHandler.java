package com.maiorovi.converter.core.handler;

import com.maiorovi.converter.core.domain.XmlAttribute;
import com.maiorovi.converter.core.domain.XmlNode;
import org.codehaus.stax2.XMLStreamReader2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StartElementEventHandler {

    public XmlNode handle(XMLStreamReader2 xmlStreamReader2) {
        XmlNode startElementView = new XmlNode(xmlStreamReader2.getName().toString());

        List<XmlAttribute> attributes = extractAttributes(xmlStreamReader2);
        startElementView.addAttributes(attributes);

        return startElementView;
    }

    private List<XmlAttribute> extractAttributes(XMLStreamReader2 xmlStreamReader2) {
        return IntStream.range(0, xmlStreamReader2.getAttributeCount())
                .mapToObj(i -> new XmlAttribute(xmlStreamReader2.getAttributeName(i).toString(), xmlStreamReader2.getAttributeValue(i)))
                .collect(Collectors.toList());
    }
}
