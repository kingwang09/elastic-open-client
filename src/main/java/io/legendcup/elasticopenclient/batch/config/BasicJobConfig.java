package io.legendcup.elasticopenclient.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class BasicJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    //@Bean
    public Job basicJob(){
        return jobBuilderFactory.get("basicJob")
                .start(basicStep())
                .build();
    }

    //@Bean
    public Step basicStep(){
        return stepBuilderFactory.get("basicStep")
                .tasklet(((contribution, chunkContext) -> {
                    Long currentTime = (Long) chunkContext.getStepContext().getJobParameters().get("time");
                    log.info("basic step: tasklet started...time={}", new Date(currentTime));
                    return RepeatStatus.FINISHED;
                })).build();
    }
}
