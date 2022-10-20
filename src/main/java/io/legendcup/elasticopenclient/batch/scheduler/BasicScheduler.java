package io.legendcup.elasticopenclient.batch.scheduler;

import io.legendcup.elasticopenclient.batch.config.BasicJobConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class BasicScheduler {
    private final JobLauncher jobLauncher;

    private final BasicJobConfig basicJobConfig;


    //@Scheduled(cron = "30 * * * * *")
    public void runBasicJob(){
        Map<String, JobParameter> parameterMap = new HashMap<>();
        parameterMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(parameterMap);
        log.info("job params: {}", jobParameters);
        try {
            jobLauncher.run(basicJobConfig.basicJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            log.error("JobExecutionAlreadyRunningException: {}", e.getMessage(), e);
        } catch (JobRestartException e) {
            log.error("JobRestartException: {}", e.getMessage(), e);
        } catch (JobInstanceAlreadyCompleteException e) {
            log.error("JobInstanceAlreadyCompleteException: {}", e.getMessage(), e);
        } catch (JobParametersInvalidException e) {
            log.error("JobParametersInvalidException: {}", e.getMessage(), e);
        }
    }
}
