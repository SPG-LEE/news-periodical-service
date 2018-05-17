package sq.news.periodical.entity;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import sq.base.BaseEntity;
import sq.base.WithName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 期刊
 * 
 * 
 * {name:"",loginName:"",lastLoginDate:""}
 */
@ApiModel(value = "期刊")
@Entity
@Table(name = "periodicals")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicInsert
@DynamicUpdate
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Periodical extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "期刊";
	@ApiModelProperty(value = "期刊标题")
	@JsonProperty
	private String title;
	@ApiModelProperty(value = "期刊摘要")
	@JsonProperty
	private String description;
	@ApiModelProperty(value = "作者名")
	@JsonProperty
	private String author;
	@ApiModelProperty(value = "作者id")
	@JsonProperty
	private long authorId;
	@ApiModelProperty(value = "发布日期")
	@JsonProperty
	private Date publishDate;
	@ApiModelProperty(value = "是否阅读")
	@JsonProperty
	private boolean hasRead;
	@ApiModelProperty(value = "是否发布")
	@JsonProperty
	private boolean hasPublish;
	@ApiModelProperty(value = "是否分享")
	@JsonProperty
	private boolean hasShare;
	@ApiModelProperty(value = "是否审核")
	@JsonProperty
	private boolean hasAudit;
	@ApiModelProperty(value = "阅读数量")
	@JsonProperty
	private long readCount;
	@ApiModelProperty(value = "是否审核")
	@JsonProperty
	private List<PeriodicalEdition> editions = new ArrayList<>();

	public long getReadCount() {
		return readCount;
	}

	public void setReadCount(long readCount) {
		this.readCount = readCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
@Column(length = 1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public boolean isHasRead() {
		return hasRead;
	}

	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}

	public boolean isHasPublish() {
		return hasPublish;
	}

	public void setHasPublish(boolean hasPublish) {
		this.hasPublish = hasPublish;
	}

	public boolean isHasShare() {
		return hasShare;
	}

	public void setHasShare(boolean hasShare) {
		this.hasShare = hasShare;
	}

	public boolean isHasAudit() {
		return hasAudit;
	}

	public void setHasAudit(boolean hasAudit) {
		this.hasAudit = hasAudit;
	}

    @Transient
	public List<PeriodicalEdition> getEditions() {
		return editions;
	}

	public void setEditions(List<PeriodicalEdition> editions) {
		this.editions = editions;
	}
}
