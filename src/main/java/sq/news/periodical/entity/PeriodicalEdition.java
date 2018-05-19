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

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 版次
 * 
 * 
 * {name:"",loginName:"",lastLoginDate:""}
 */
@ApiModel(value = "版次")
@Entity
@Table(name = "periodical_editions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicInsert
@DynamicUpdate
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class PeriodicalEdition extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "版次";
	@ApiModelProperty(value = "版次标题")
	@JsonProperty
	private String title;
	@ApiModelProperty(value = "期刊Id")
	@JsonProperty
	private long periodicalId;
	@ApiModelProperty(value = "期刊名称")
	@JsonProperty
	private String periodicalName;
	@ApiModelProperty(value = "作者名")
	@JsonProperty
	private String author;
	@ApiModelProperty(value = "作者id")
	@JsonProperty
	private long authorId;
	@ApiModelProperty(value = "发布日期")
	@JsonProperty
	private Date publishDate;
	@ApiModelProperty(value = "图片")
	@JsonProperty
	private String image;
	@ApiModelProperty(value = "图片名称")
	@JsonProperty
	private String imageName;

	@ApiModelProperty(value = "阅读数量")
	@JsonProperty
	private long readCount;

	@ApiModelProperty(value = "阅读数量")
	@JsonProperty
	private List<HotZone> hotZones = new ArrayList<>();

	public String getPeriodicalName() {
		return periodicalName;
	}

	public void setPeriodicalName(String periodicalName) {
		this.periodicalName = periodicalName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getPeriodicalId() {
		return periodicalId;
	}

	public void setPeriodicalId(long periodicalId) {
		this.periodicalId = periodicalId;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public long getReadCount() {
		return readCount;
	}

	public void setReadCount(long readCount) {
		this.readCount = readCount;
	}

	@Transient
	public List<HotZone> getHotZones() {
		return hotZones;
	}

	public void setHotZones(List<HotZone> hotZones) {
		this.hotZones = hotZones;
	}
}
