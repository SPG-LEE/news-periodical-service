package sq.news.periodical.service;

import org.springframework.scheduling.annotation.Scheduled;
import sq.base.ServiceResult;
import sq.news.periodical.entity.Article;
import sq.news.periodical.service.impl.JobServiceImpl;

import java.util.List;

public interface JobService {
	void synUser();
    void synUserTest();
}
