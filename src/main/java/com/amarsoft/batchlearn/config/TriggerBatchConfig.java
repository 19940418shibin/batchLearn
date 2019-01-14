package com.amarsoft.batchlearn.config;

import com.amarsoft.batchlearn.listener.CsvJobListener;
import com.amarsoft.batchlearn.model.Person;
import com.amarsoft.batchlearn.processor.CsvItemProcessor;
import com.amarsoft.batchlearn.util.CustomLineAggregator;
import com.amarsoft.batchlearn.util.DefaultFlatFileFooterCallback;
import com.amarsoft.batchlearn.util.DefaultFlatFileHeaderCallback;
import com.amarsoft.batchlearn.validator.CsvBeanValidator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 定时跑批配置文件
 */
@Configuration
@EnableBatchProcessing
public class TriggerBatchConfig {

    @Bean
    @StepScope
    //此处 需 注意 Bean 的 类型 修改 为 FlatFileItemReader， 而 不是 ItemReader。否则会报错
    public FlatFileItemReader<Person> reader(@Value("#{jobParameters['input.file.name']}") String pathToFile) throws Exception {
        //使用FlatFileItemReader读取文件
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        reader.setResource(new ClassPathResource(pathToFile));
        //在此处对CVS文件的数据和领域模型类做对应映射
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "name","age", "nation" ,"address"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<Person, Person> processor() {
        //使用自定义的ItemProcessor的实现
        CsvItemProcessor processor = new CsvItemProcessor();
        //为Processor指定校验器
        processor.setValidator(csvBeanValidator());
        return processor;
    }

    @Bean
    //Spring能让容器中已有的Bean以参数的形式注入，Spring boot已经定义了DataSource
    public ItemWriter<Person> writer() {
        FlatFileItemWriter<Person> writer = new FlatFileItemWriter<Person>();
        writer.setResource(new FileSystemResource("D:\\batchlearn\\src\\main\\resources\\peopleout.csv"));
        //使用自定义的LineAggregator
        writer.setLineAggregator(new CustomLineAggregator<Person>(){{
            setNames(new String[] { "name","age", "nation" ,"address"});
            setFieldExtractor(new BeanWrapperFieldExtractor<Person>(){{
                setNames(new String[] { "name","age", "nation" ,"address"});
            }});
        }});
        //在文件头部写入自定义的内容
        writer.setHeaderCallback(new DefaultFlatFileHeaderCallback());
        //在文件尾部写入自定义的内容
        writer.setFooterCallback(new DefaultFlatFileFooterCallback());
        return writer;
    }

    @Bean
    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        return jobRepositoryFactoryBean.getObject();
    }

    @Bean
    public SimpleJobLauncher jobLauncher(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository(dataSource, transactionManager));
        return jobLauncher;
    }

    @Bean
    public Job importJob(JobBuilderFactory jobs, Step s1) {
        return jobs.get("importJob")
                .incrementer(new RunIdIncrementer())
                //指定step
                .flow(s1)
                .end()
                //绑定监听器
                .listener(csvJobListener())
                .build();
    }


    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader, ItemWriter<Person> writer,
                      ItemProcessor<Person,Person> processor) {
        return stepBuilderFactory
                .get("step1")
                .<Person, Person> chunk(2000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public CsvJobListener csvJobListener() {
        return new CsvJobListener();
    }

    @Bean
    public Validator<Person> csvBeanValidator() {
        return new CsvBeanValidator<Person>();
    }
}
