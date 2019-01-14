package com.amarsoft.batchlearn.writer;

import com.amarsoft.batchlearn.model.Person;
import com.amarsoft.batchlearn.util.CustomLineAggregator;
import com.amarsoft.batchlearn.util.DefaultFlatFileFooterCallback;
import com.amarsoft.batchlearn.util.DefaultFlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

/**
 *写入flat文件
 */
public class PersonFlatFileItemWriter extends FlatFileItemWriter<Person> {
    public PersonFlatFileItemWriter() {
                //注意不可以使用ClassPathResource
       // writer.setResource(new ClassPathResource("peopleout.csv"));
        this.setResource(new FileSystemResource("D:\\batchlearn\\src\\main\\resources\\peopleout.csv"));
//        this.setLineAggregator(new DelimitedLineAggregator<Person>(){{
//            setDelimiter(",");
//            setFieldExtractor(new BeanWrapperFieldExtractor<Person>(){{
//                setNames(new String[] { "name","age", "nation" ,"address"});
//            }});
//        }});
        //使用自定义的LineAggregator
        this.setLineAggregator(new CustomLineAggregator<Person>(){{
            setNames(new String[] { "name","age", "nation" ,"address"});
            setFieldExtractor(new BeanWrapperFieldExtractor<Person>(){{
                setNames(new String[] { "name","age", "nation" ,"address"});
            }});
        }});
        //在文件头部写入自定义的内容
        this.setHeaderCallback(new DefaultFlatFileHeaderCallback());
        //在文件尾部写入自定义的内容
        this.setFooterCallback(new DefaultFlatFileFooterCallback());
    }
}
