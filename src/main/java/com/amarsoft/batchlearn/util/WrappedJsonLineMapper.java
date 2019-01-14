package com.amarsoft.batchlearn.util;

import com.amarsoft.batchlearn.model.Person;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.JsonLineMapper;

import java.util.Map;

public class WrappedJsonLineMapper implements LineMapper<Person> {

    private JsonLineMapper delegate;

    @Override
    public Person mapLine(String line, int lineNumber) throws Exception {
        Person person = new Person();
        Map<String,Object> personMap = delegate.mapLine(line,lineNumber);
        person.setName((String) personMap.get("name"));
        person.setAge((int) personMap.get("age"));
        person.setNation((String) personMap.get("nation"));
        person.setAddress((String) personMap.get("address"));
        return person;
    }

    public JsonLineMapper getDelegate() {
        return delegate;
    }

    public void setDelegate(JsonLineMapper delegate) {
        this.delegate = delegate;
    }
}
