package sq.news.periodical.bean.weixin;

import java.util.List;

public class UserResponse extends WeixinResponse {
    private List<UserBean> userlist;

    public List<UserBean> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<UserBean> userlist) {
        this.userlist = userlist;
    }
}
