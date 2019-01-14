package com.amarsoft.batchlearn.reader.flat;

import com.amarsoft.batchlearn.model.Person;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 读取多文件
 */
public class MultipleResourceItemReader extends MultiResourceItemReader<Person> {
    public MultipleResourceItemReader() {
        this.setResources(new Resource[]{new ClassPathResource("people.csv"),
                new ClassPathResource("people-0.csv"),new ClassPathResource("people-1.csv")});
        this.setDelegate(new FlatFileItemReader<Person>(){{
            setStrict(true);
            setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "name","age", "nation" ,"address"});
                }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
                }});
            }});
        }});
    }
}
