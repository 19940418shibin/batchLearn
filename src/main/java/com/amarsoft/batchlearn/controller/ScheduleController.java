package com.amarsoft.batchlearn.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时执行跑批任务
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job importJob;
    public JobParameters jobParameters;

    /**
     * 每10分钟执行一次跑批
     * @return
     * @throws Exception
     */
    //https://blog.csdn.net/jack_bob/article/details/78786740了解定时配置
    @Scheduled(cron = "0 0/2 * * * ?")
    public String imp() throws Exception{
        String path = "people.csv";
        Date date = new Date();
        jobParameters = new JobParametersBuilder()
                .addLong("time",date.getTime())
                .addString("input.file.name",path)
                .toJobParameters();
        jobLauncher.run(importJob,jobParameters);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        System.out.println("输出时间为： "+str);
        return "ok";
    }
}
