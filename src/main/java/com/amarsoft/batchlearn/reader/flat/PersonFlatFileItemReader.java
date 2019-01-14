package com.amarsoft.batchlearn.reader.flat;

import com.amarsoft.batchlearn.model.Person;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

/**
 * 读取分隔符文件reader
 */
public class PersonFlatFileItemReader  extends FlatFileItemReader<Person> {
    public PersonFlatFileItemReader() {
        //使用FlatFileItemReader的setResource方法设置CSV文件的路径
        this.setResource(new ClassPathResource("people.csv"));
        //在此处对CVS文件的数据和领域模型类做对应映射
        this.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "name","age", "nation" ,"address"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
    }
}
