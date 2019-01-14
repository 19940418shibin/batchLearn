package com.amarsoft.batchlearn.writer;

import com.amarsoft.batchlearn.model.Person;
import com.amarsoft.batchlearn.util.FooterStaxWriterCallback;
import com.amarsoft.batchlearn.util.HeaderStaxWriterCallback;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

/**
 * 写入xml文件
 */
public class XMLItemWriter extends StaxEventItemWriter<Person>{
    public XMLItemWriter() {
        this.setResource(new FileSystemResource("D:\\batchlearn\\src\\main\\resources\\peopleout.xml"));
        this.setMarshaller(new XStreamMarshaller(){{
            Map<String,Class<Person>> map = new HashMap<String, Class<Person>>();
            map.put("person",Person.class);
            setAliases(map);
        }});
        this.setHeaderCallback(new HeaderStaxWriterCallback());
        this.setFooterCallback(new FooterStaxWriterCallback());
    }
}
