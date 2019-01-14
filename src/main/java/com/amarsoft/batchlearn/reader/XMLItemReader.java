package com.amarsoft.batchlearn.reader;

import com.amarsoft.batchlearn.model.Person;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

/**
 * 读取xml文件
 */
public class XMLItemReader extends StaxEventItemReader<Person> {
    public XMLItemReader() {
        this.setResource(new ClassPathResource("people.xml"));
        this.setFragmentRootElementName("person");
        this.setUnmarshaller(new XStreamMarshaller(){{
            Map<String,Class<Person>> map = new HashMap<String,Class<Person>>();
            map.put("person",Person.class);
            setAliases(map);
        }});
    }
}
