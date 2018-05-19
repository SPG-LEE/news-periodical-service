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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 评论
 * 
 * 
 * {name:"",loginName:"",lastLoginDate:""}
 */
@ApiModel(value = "评论")
@Entity
@Table(name = "article_comments")
@DynamicInsert
@DynamicUpdate
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class ArticleComment extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "评论";
	@ApiModelProperty(value = "文章Id")
	@JsonProperty
	private long articleId;
	@ApiModelProperty(value = "评论人名称")
	@JsonProperty
	private String userName;
	@ApiModelProperty(value = "评论人头像")
	@JsonProperty
	private String avtar;
	@ApiModelProperty(value = "评论人Id")
	@JsonProperty
	private String userId;
	@ApiModelProperty(value = "评论内容")
	@JsonProperty
	private String comment;
	@ApiModelProperty(value = "是否展示")
	@JsonProperty
	private boolean hasShow = true;

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvtar() {
		return avtar;
	}

	public void setAvtar(String avtar) {
		this.avtar = avtar;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(columnDefinition="TEXT")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isHasShow() {
		return hasShow;
	}

	public void setHasShow(boolean hasShow) {
		this.hasShow = hasShow;
	}
}
