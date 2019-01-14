package com.amarsoft.batchlearn.reader.flat;

import com.amarsoft.batchlearn.model.Person;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.io.ClassPathResource;

/**
 * 读取定长型文件
 */
public class FixedFlatFileItemReader extends FlatFileItemReader<Person> {
    public FixedFlatFileItemReader() {
        this.setResource(new ClassPathResource("people1.csv"));
        this.setLineMapper(new DefaultLineMapper<Person>(){{
            setLineTokenizer(new FixedLengthTokenizer(){{
                setColumns(new Range[] {new Range(1, 6), new Range(7, 8), new Range(9,14),new Range(15)});
                setNames(new String[] { "name","age", "nation" ,"address"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){{
                setTargetType(Person.class);
            }});
        }});
    }
}
