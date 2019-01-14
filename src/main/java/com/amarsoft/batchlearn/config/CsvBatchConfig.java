package com.amarsoft.batchlearn.config;

import com.amarsoft.batchlearn.listener.CsvJobListener;
import com.amarsoft.batchlearn.model.Person;
import com.amarsoft.batchlearn.processor.CsvItemProcessor;
import com.amarsoft.batchlearn.reader.XMLItemReader;
import com.amarsoft.batchlearn.reader.flat.*;
import com.amarsoft.batchlearn.reader.jdbc.JdbcItemReader;
import com.amarsoft.batchlearn.reader.jdbc.JdbcPageItemReader;
import com.amarsoft.batchlearn.reader.jdbc.ProcedureItemReader;
import com.amarsoft.batchlearn.util.FooterStaxWriterCallback;
import com.amarsoft.batchlearn.validator.CsvBeanValidator;
import com.amarsoft.batchlearn.writer.JdbcItemWriter;
import com.amarsoft.batchlearn.writer.PersonFlatFileItemWriter;
import com.amarsoft.batchlearn.writer.XMLItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * batch配置文件
 * 项目启动自动执行跑批任务
 */
//@Configuration
//@EnableBatchProcessing
public class CsvBatchConfig {
    @Autowired
    private JobBuilderFactory jobs;
    @Autowired
    private StepBuilderFactory steps;

    /**
     * 用来读取数据的接口
     * @return
     * @throws Exception
     */
    @Bean
    public ItemReader<Person> reader(DataSource dataSource) throws Exception {
        PersonFlatFileItemReader reader = new PersonFlatFileItemReader();
//        MultipleFlatFileItemReader reader = new MultipleFlatFileItemReader();
//        FixedFlatFileItemReader reader = new FixedFlatFileItemReader();
//        JsonFlatFileItemReader reader = new JsonFlatFileItemReader();
//        XMLItemWriter reader = new XMLItemWriter();
//        MultipleResourceItemReader reader = new MultipleResourceItemReader();
//        JdbcItemReader reader = new JdbcItemReader(dataSource);
//        ProcedureItemReader reader = new ProcedureItemReader(dataSource);
//        JdbcPageItemReader reader = new JdbcPageItemReader(dataSource);

        return reader;
    }

    /**
     * 用来处理数据的接口
     * @return
     */
    @Bean
    public ItemProcessor<Person, Person> processor() {
        //使用自定义的ItemProcessor的实现
        CsvItemProcessor processor = new CsvItemProcessor();
        //为Processor指定校验器
        processor.setValidator(csvBeanValidator());
        return processor;
    }

    /**
     * 用来输出数据的接口
     * @return
     */
    @Bean
    public ItemWriter<Person> writer(DataSource dataSource) {
//        PersonFlatFileItemWriter writer = new PersonFlatFileItemWriter();
        XMLItemWriter writer = new XMLItemWriter();
//        JdbcItemWriter writer = new JdbcItemWriter(dataSource);
        return writer;
    }

    /**
     * 用来注册Job的容器
     * @param dataSource
     * @param transactionManager
     * @return
     * @throws Exception
     */
    @Bean
    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
         jobRepositoryFactoryBean.setDatabaseType("mysql");
        return jobRepositoryFactoryBean.getObject();
    }

    /**
     * 用来启动Job的接口
     * @param dataSource
     * @param transactionManager
     * @return
     * @throws Exception
     */
    @Bean
    public SimpleJobLauncher jobLauncher(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository(dataSource, transactionManager));
        return jobLauncher;
    }

    /**
     * 实际执行的任务，包含一个或多个Step
     * @param s1
     * @return
     */
    @Bean
    public Job importJob(Step s1){
        return jobs.get("importJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1)
                .end()
                .listener(csvJobListener())
                .build();
    }

    /**
     * Step 步骤，包含ItemReader、ItemProcessor、ItemWriter
     * @param reader
     * @param writer
     * @param processor
     * @return
     */
    @Bean
    public Step step1(ItemReader<Person> reader, ItemWriter<Person> writer,ItemProcessor<Person,Person> processor){
        return steps
               .get("step1")
                .<Person,Person>chunk(2000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new FooterStaxWriterCallback())
                .build();
    }

    /**
     * Job监听器
     * @return
     */
    @Bean
    public CsvJobListener csvJobListener() {
        return new CsvJobListener();
    }

    @Bean
    public Validator<Person> csvBeanValidator() {
        return new CsvBeanValidator<Person>();
    }
}
