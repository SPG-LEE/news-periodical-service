package sq.news.periodical.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import sq.base.BaseEntity;
import sq.news.periodical.bean.weixin.DepartmentBean;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 文章
 *
 *
 * {name:"",loginName:"",lastLoginDate:""}
 */
@ApiModel(value = "部门")
@Entity
@Table(name = "departments")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicInsert
@DynamicUpdate
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Department extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "部门";
	@ApiModelProperty(value = "部门id")
	@JsonProperty
	private String departmentId;
	@ApiModelProperty(value = "部门名称")
	@JsonProperty
	private String name;
    @ApiModelProperty(value = "父部门")
    @JsonProperty
    private String parentId;
    @ApiModelProperty(value = "排序")
    @JsonProperty
    private String sort;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    public void copyFrom(DepartmentBean departmentBean){
	    this.setDepartmentId(departmentBean.getId()+"");
	    this.setName(departmentBean.getName());
	    this.setSort(departmentBean.getOrder()+"");
	    this.setParentId(departmentBean.getParentid()+"");
    }
}
