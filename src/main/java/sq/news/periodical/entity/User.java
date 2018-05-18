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
import sq.news.periodical.bean.weixin.UserBean;
import sq.util.JsonUtil;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 文章
 *
 *
 * {name:"",loginName:"",lastLoginDate:""}
 */
@ApiModel(value = "用户")
@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicInsert
@DynamicUpdate
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "用户";
	@ApiModelProperty(value = "userId")
	@JsonProperty
	private String userId;
	@ApiModelProperty(value = "姓名")
	@JsonProperty
	private String name;
	@ApiModelProperty(value = "职位")
	@JsonProperty
	private String position;
	@ApiModelProperty(value = "手机号")
	@JsonProperty
	private String mobile;
	@ApiModelProperty(value = "email")
	@JsonProperty
	private String email;
	@ApiModelProperty(value = "头像")
	@JsonProperty
	private String avatar;
	@ApiModelProperty(value = "用户状态")
	@JsonProperty
	private int status;
	@ApiModelProperty(value = "部门列表")
	@JsonProperty
	private String department;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	public void copyFrom(UserBean userBean){
	    this.setAvatar(userBean.getAvatar());
        this.setDepartment(JsonUtil.toJson(userBean.getDepartment()));
        this.setEmail(userBean.getEmail());
        this.setMobile(userBean.getMobile());
        this.setName(userBean.getName());
        this.setPosition(userBean.getPosition());
        this.setStatus(userBean.getStatus());
        this.setUserId(userBean.getUserid());
    }
}
