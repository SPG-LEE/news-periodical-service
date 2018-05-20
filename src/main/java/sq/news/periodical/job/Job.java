package sq.news.periodical.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sq.news.periodical.service.JobService;

@Component
public class Job {
    @Autowired
    private JobService jobService;
    private final static String SYN_USER_CRON = "31 31 12 * * ?";

    @Scheduled(cron = SYN_USER_CRON)
    public void synUser(){
        jobService.synUser();
    }
}
