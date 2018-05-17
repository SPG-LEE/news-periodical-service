package sq.news.periodical.service;

import sq.base.AppResult;
import sq.bean.Admin;

public interface AdminRedisService {
	AppResult<Admin> getAdmin(String token);

	AppResult<Boolean> hasPermission(String token, String permission);



	
	
}
