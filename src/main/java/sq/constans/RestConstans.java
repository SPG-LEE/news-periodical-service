package sq.constans;

public enum RestConstans {
    FIND_SUCCESS("操作成功"),FIND_FAILED("查询失败"),NO_ADMIN("没有此管理员"),NO_PERMISSION("没有权限")
    ,SUBMIT_ERROR("错误请求");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private RestConstans(String name){
        this.name = name;
    }
}