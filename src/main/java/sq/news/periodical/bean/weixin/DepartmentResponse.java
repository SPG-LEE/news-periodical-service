package sq.news.periodical.bean.weixin;

import java.util.ArrayList;
import java.util.List;

public class DepartmentResponse  extends WeixinResponse{
    private List<DepartmentBean> department;

    public List<DepartmentBean> getDepartment() {
        return department;
    }

    public void setDepartment(List<DepartmentBean> department) {
        this.department = department;
    }
}
