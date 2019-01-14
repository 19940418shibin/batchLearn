package com.amarsoft.batchlearn.util;

import org.springframework.batch.item.xml.StaxWriterCallback;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * 在输出的xml文件头部加入内容
 */
public class HeaderStaxWriterCallback implements StaxWriterCallback {
    @Override
    public void write(XMLEventWriter xmlEventWriter) throws IOException {
        XMLEventFactory factory = XMLEventFactory.newInstance();
        try {
            xmlEventWriter.add(factory.createComment("person 20181224 begin"));
        }catch (XMLStreamException e){
            e.printStackTrace();
        }
    }
}
