package sq.news.periodical.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import sq.news.periodical.constants.PermissionConstants;
import sq.news.periodical.constants.SysConstants;
import sq.util.HttpUtils;
import sq.util.JsonUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class QyWeixinUtil {
    private static String CORPID = "wx53450f364bdfd34e";
    private static String TEST_CORPSECRET = "bJVjvAe61kcxATDqTm8PuMhT8vc3ewtub7THA-1eE6M";
    private static String TEST_AGENTID = "1000006";
    private static String PROD_CORPSECRET = "SRwcTJL1UnpwFt2NaHxaYZR8CjIRUMyFCcXKxVhb-6Q";
    private static String GET_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    private static String GET_USERS_FROM_DEPARTMENTID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/list";
    private static String GET_DEPARTMENTS_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list";
    private static String GET_USER_FROM_ID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get";
    private static String GET_USERID_FROM_CODE_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
    private static String POST_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
    private static String GET_AUTHCODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";

    public static String getToken(RedisTemplate redisTemplate, String secret) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String redisToken = ops.get(secret);
        if (redisToken != null) {
            return redisToken;
        }
        Map<String, String> params = new HashMap<>();
        params.put("corpid", CORPID);
        params.put("corpsecret", secret);
        try {
            String responseBody = HttpUtils.httpGetMethod(GET_TOKEN_URL, params);
            if (responseBody != null) {
                JSONObject tokenBody = JSONObject.parseObject(responseBody);
                redisToken = tokenBody.getString("access_token");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (redisToken != null) {
            ops.set(secret, redisToken);
            redisTemplate.expire(secret, 7000, TimeUnit.SECONDS);
            return redisToken;
        }
        return null;
    }

    public static String getToken(RedisTemplate redisTemplate) {
        return getToken(redisTemplate, PROD_CORPSECRET);
    }

    public static String getDepartments(RedisTemplate redisTemplate) {
        String token = getToken(redisTemplate);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", token);
        try {
            return HttpUtils.httpGetMethod(GET_DEPARTMENTS_URL, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUsersByDepartmentId(RedisTemplate redisTemplate, String departmentId) {
        String token = getToken(redisTemplate);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", token);
        params.put("department_id", departmentId);
        params.put("fetch_child", "0");
        params.put("status", "0");
        try {
            return HttpUtils.httpGetMethod(GET_USERS_FROM_DEPARTMENTID_URL, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUserById(RedisTemplate redisTemplate, String userId) {
        String token = getToken(redisTemplate);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", token);
        params.put("userid", userId);
        try {
            return HttpUtils.httpGetMethod(GET_USER_FROM_ID_URL, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUserIdByAuthCode(RedisTemplate redisTemplate, String code) {
        String token = getToken(redisTemplate,TEST_CORPSECRET);
        Map<String, String> params = new HashMap<>();
        params.put("access_token", token);
        params.put("code", code);
        try {
            return HttpUtils.httpGetMethod(GET_USERID_FROM_CODE_URL, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendMessage(RedisTemplate redisTemplate, String url, String picurl,long id, String title, String description, String departmentId, String userId) {
        String token = getToken(redisTemplate,TEST_CORPSECRET);
        JSONObject params = new JSONObject();
        if (userId != null) {
            params.put("touser", userId);
        }
        if (departmentId != null) {
            params.put("toparty", departmentId);
        }
        params.put("msgtype", "news");
        params.put("agentid", TEST_AGENTID);
        JSONObject news = new JSONObject();
        JSONArray articles = new JSONArray();
        JSONObject article = new JSONObject();
        article.put("title", title);
        article.put("description", description);
        String redirectUrl = null;
        try {
            redirectUrl = GET_AUTHCODE_URL + "?appid=" + CORPID + "&redirect_uri=" + URLEncoder.encode(url, "UTF-8") + "&response_type=code&scope=snsapi_base&state="+id+"#wechat_redirect";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        article.put("url", redirectUrl);
        article.put("picurl", picurl);
        articles.add(article);
        news.put("articles", articles);
        params.put("news", news);
        try {
            return HttpUtils.httpPostMethod(POST_MESSAGE_URL + "?access_token=" + token, new HashMap<>(), params.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
