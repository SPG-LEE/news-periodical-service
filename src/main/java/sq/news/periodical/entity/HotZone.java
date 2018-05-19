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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 热区
 * 
 * 
 * {name:"",loginName:"",lastLoginDate:""}
 */
@ApiModel(value = "热区")
@Entity
@Table(name = "hotzones")
@DynamicInsert
@DynamicUpdate
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class HotZone extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "热区";
	@ApiModelProperty(value = "热区标题")
	@JsonProperty
	private String title;
	@ApiModelProperty(value = "热区坐标")
	@JsonProperty
	private String mapInfo;
	@ApiModelProperty(value = "文章Id")
	@JsonProperty
	private long articleId;
	@ApiModelProperty(value = "版次Id")
	@JsonProperty
	private long editionId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMapInfo() {
		return mapInfo;
	}

	public void setMapInfo(String mapInfo) {
		this.mapInfo = mapInfo;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public long getEditionId() {
		return editionId;
	}

	public void setEditionId(long editionId) {
		this.editionId = editionId;
	}
}
