package sq.news.periodical.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import sq.base.AppResult;
import sq.bean.Admin;
import sq.constans.RestConstans;
import sq.news.periodical.service.AdminRedisService;
import sq.util.AppResultBuilder;
import sq.util.PermissionUtil;

@Repository
public class AdminRedisServiceImpl implements AdminRedisService {

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public AppResult<Admin> getAdmin(String token) {
		PermissionUtil instance = PermissionUtil.getInstance(redisTemplate);
		Admin admin = instance.getAdmin(token);
		if (admin == null) {
			return AppResultBuilder.buildFailedMessageResult(RestConstans
					.NO_ADMIN.getName());
		}
		return AppResultBuilder.buildSuccessMessageResult(admin,
				RestConstans.FIND_SUCCESS.getName());
	}

	@Override
	public AppResult<Boolean> hasPermission(String token, String permission) {
		PermissionUtil instance = PermissionUtil.getInstance(redisTemplate);
		boolean hasPermission = instance.hasPermission(token, permission);
		if (!hasPermission) {
			return AppResultBuilder.buildFailedMessageResult(hasPermission,
					RestConstans.NO_PERMISSION.getName());
		}
		return AppResultBuilder.buildSuccessMessageResult(hasPermission,
				RestConstans.FIND_SUCCESS.getName());
	}

}
