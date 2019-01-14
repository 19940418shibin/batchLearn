package com.amarsoft.batchlearn.reader.flat;

import com.amarsoft.batchlearn.model.Person;
import com.amarsoft.batchlearn.util.MultiLineRecordSeparatorPolicy;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

/**
 * 读取记录跨多行文件
 */
public class MultipleFlatFileItemReader extends FlatFileItemReader{
    public MultipleFlatFileItemReader() {
        this.setResource(new ClassPathResource("people3.csv"));
        this.setRecordSeparatorPolicy(new MultiLineRecordSeparatorPolicy(){{
            setDelimiter(",");
            setCount(3);
        }});
        this.setLineMapper(new DefaultLineMapper<Person>(){{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setDelimiter(",");
                setNames(new String[] { "name","age", "nation" ,"address"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){{
                setTargetType(Person.class);
            }});
        }});
    }
}
