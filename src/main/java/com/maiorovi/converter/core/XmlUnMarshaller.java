package com.maiorovi.converter.core;

import com.maiorovi.converter.core.domain.XmlNode;
import com.maiorovi.converter.core.file.processing.FileReader;
import com.maiorovi.converter.core.handler.StartElementEventHandler;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

public class XmlUnMarshaller {

    private StartElementEventHandler startElementEventHandler = new StartElementEventHandler();

    public XmlNode unmarshall(String filePath) {
        try {
            return doUnmarshall(filePath);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    public XmlNode doUnmarshall(String filePath) throws XMLStreamException {
        XMLStreamReader2 xmlStreamReader = createXmlStreamReader(filePath);
        XmlNode tmpNode = null;


        while(xmlStreamReader.hasNext()){
            int eventType = xmlStreamReader.next();
            switch (eventType) {
                case XMLEvent.START_ELEMENT:
                    XmlNode currentNode = startElementEventHandler.handle(xmlStreamReader);

                    if(tmpNode != null) {
                        tmpNode.addChild(currentNode);
                        currentNode.setParent(tmpNode);
                        tmpNode = currentNode;
                    } else {
                        tmpNode = currentNode;
                    }

                    break;
                case XMLEvent.CHARACTERS:
                    tmpNode.setContent(xmlStreamReader.getText());
                    break;
                case XMLEvent.END_ELEMENT:
                    XmlNode parent = tmpNode.getParent();
                    if (parent != null) {
                        tmpNode = parent;
                    }

                    break;
                default:
                    //do nothing
                    break;
            }
        }

        return tmpNode;
    }

    private XMLStreamReader2 createXmlStreamReader(String filePath) throws XMLStreamException {
        FileReader fileReader = new FileReader(filePath);
        InputStream xmlInputStream = fileReader.getInputStream();
        XMLInputFactory2 xmlInputFactory = (XMLInputFactory2) XMLInputFactory.newInstance();
            return (XMLStreamReader2) xmlInputFactory.createXMLStreamReader(xmlInputStream);
    }
}
