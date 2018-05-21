package sq.news.periodical.service;

import sq.news.periodical.bean.PublishMessageBean;
import sq.news.periodical.entity.User;

public interface JobService {
	void synUser();
    User createVisitor();
    void synUserTest();

    User getUserByAuthCode(String code);

    String sendMessage(PublishMessageBean publishMessageBean);
}
