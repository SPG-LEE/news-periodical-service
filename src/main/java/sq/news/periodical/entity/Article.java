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

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文章
 * <p>
 * <p>
 * {name:"",loginName:"",lastLoginDate:""}
 */
@ApiModel(value = "文章")
@Entity
@Table(name = "aiticles")
@DynamicInsert
@DynamicUpdate
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Article extends BaseEntity {

    private static final long serialVersionUID = 1L;
    public static final String NAME = "文章";
    @ApiModelProperty(value = "文章标题")
    @JsonProperty
    private String title;
    @ApiModelProperty(value = "文章摘要")
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
    @ApiModelProperty(value = "期刊Id")
    @JsonProperty
    private long periodicalId;
    @ApiModelProperty(value = "期刊标题")
    @JsonProperty
    private String periodical;
    @ApiModelProperty(value = "版次Id")
    @JsonProperty
    private long editionId;
    @ApiModelProperty(value = "正文")
    @JsonProperty
    private String content;

    private byte[] contentByte;
    @ApiModelProperty(value = "版次名")
    @JsonProperty
    private String editionPeriod;
    @ApiModelProperty(value = "封面图片")
    @JsonProperty
    private String mainImage;
    @ApiModelProperty(value = "是否审核")
    @JsonProperty
    private boolean hasAudit;
    @ApiModelProperty(value = "是否显示")
    @JsonProperty
    private boolean hasShow;
    @ApiModelProperty(value = "评论")
    @JsonProperty
    private List<ArticleComment> comments = new ArrayList<>();

    public boolean isHasAudit() {
        return hasAudit;
    }

    public void setHasAudit(boolean hasAudit) {
        this.hasAudit = hasAudit;
    }

    public boolean isHasShow() {
        return hasShow;
    }

    public void setHasShow(boolean hasShow) {
        this.hasShow = hasShow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public long getPeriodicalId() {
        return periodicalId;
    }

    public void setPeriodicalId(long periodicalId) {
        this.periodicalId = periodicalId;
    }

    public long getEditionId() {
        return editionId;
    }

    public void setEditionId(long editionId) {
        this.editionId = editionId;
    }

    @Column(columnDefinition = "BLOB")
    public byte[] getContentByte() {
        return contentByte;
    }

    public void setContentByte(byte[] contentByte) {
        if (contentByte != null) {
            try {
                this.content = new String(contentByte, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        this.contentByte = contentByte;
    }

    @Transient
    public String getContent() {
        if (contentByte != null) {
            try {
                return new String(contentByte, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    public void setContent(String content) {
        try {
            contentByte = content.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getEditionPeriod() {
        return editionPeriod;
    }

    public void setEditionPeriod(String editionPeriod) {
        this.editionPeriod = editionPeriod;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    @Transient
    public List<ArticleComment> getComments() {
        return comments;
    }

    public void setComments(List<ArticleComment> comments) {
        this.comments = comments;
    }

    public String getPeriodical() {
        return periodical;
    }

    public void setPeriodical(String periodical) {
        this.periodical = periodical;
    }
}
