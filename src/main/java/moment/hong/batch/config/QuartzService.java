package moment.hong.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moment.hong.batch.job.QuartzJob;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzService {
    private final Scheduler scheduler;

    @PostConstruct
    public void init() {
        try {
            //스케줄러 초기화 -> DB도 CLAER
            scheduler.clear();
            //Job 리스너 등록
            scheduler.getListenerManager().addJobListener(new QuartzJobListener());
            //Trigger 리스너 등록
            scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener());

            //Job에 필요한 Parameter 생성
            Map paramsMap = new HashMap<>();
            //Job의 실행횟수 및 실행시간
            paramsMap.put("executeCount", 1);
            paramsMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            //Job 생성 및 Scheduler에 등록
            addJob(QuartzJob.class, "QuartzJob", "Quartz Job 입니다", paramsMap, "0/5 * * * * ?");

        } catch (Exception e) {
            log.error("addJob error  : {}", e);

        }
    }

    //Job 추가
    public <T extends Job> void addJob(Class<? extends Job> job, String name, String dsec, Map paramsMap, String cron) throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(job, name, dsec, paramsMap);
        Trigger trigger = buildCronTrigger(cron);
        if (scheduler.checkExists(jobDetail.getKey())) scheduler.deleteJob(jobDetail.getKey());
        scheduler.scheduleJob(jobDetail, trigger);
    }

    //JobDetail 생성
    public <T extends Job> JobDetail buildJobDetail(Class<? extends Job> job, String name, String desc, Map paramsMap) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(paramsMap);

        return JobBuilder
                .newJob(job)
                .withIdentity(name)
                .withDescription(desc)
                .usingJobData(jobDataMap)
                .build();
    }

    //Trigger 생성
    private Trigger buildCronTrigger(String cronExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                .build();
    }
}