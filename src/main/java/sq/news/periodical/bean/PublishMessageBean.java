package sq.news.periodical.bean;

import java.util.List;

public class PublishMessageBean {
    private List<String> departmentIds;
    private long periodicalId;

    public List<String> getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(List<String> departmentIds) {
        this.departmentIds = departmentIds;
    }

    public long getPeriodicalId() {
        return periodicalId;
    }

    public void setPeriodicalId(long periodicalId) {
        this.periodicalId = periodicalId;
    }
}
