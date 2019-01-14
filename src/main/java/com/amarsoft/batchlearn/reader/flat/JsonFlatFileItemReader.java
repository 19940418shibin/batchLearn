package com.amarsoft.batchlearn.reader.flat;

import com.amarsoft.batchlearn.model.Person;
import com.amarsoft.batchlearn.util.WrappedJsonLineMapper;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.JsonLineMapper;
import org.springframework.batch.item.file.separator.JsonRecordSeparatorPolicy;
import org.springframework.core.io.ClassPathResource;

/**
 * 读取文件数据类型为JSON的文件
 */
public class JsonFlatFileItemReader extends FlatFileItemReader<Person> {
    public JsonFlatFileItemReader() {
        this.setResource(new ClassPathResource("people2.csv"));
        this.setRecordSeparatorPolicy(new JsonRecordSeparatorPolicy());
        this.setLineMapper(new WrappedJsonLineMapper(){{
            setDelegate(new JsonLineMapper());
        }});
    }
}
