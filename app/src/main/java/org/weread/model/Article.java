package org.weread.model;

import java.util.Date;

/**
 * Article entity. @author MyEclipse Persistence Tools
 */

public class Article implements java.io.Serializable {

	// Fields

	/**
	 *
	 */
	private static final long serialVersionUID = -9113541193993549745L;
	private Integer aid;
	private String articleType;
	private String title;
	private String author;
	private Date createTime;
	private Integer collectCount;
	private String content;
	private String sumary;
	private String imgurl;

	// Constructors

	/** default constructor */
	public Article() {
	}

	/** full constructor */
	public Article(String articleType, String title, String author,
				   Date createTime, Integer collectCount, String content) {
		this.articleType = articleType;
		this.title = title;
		this.author = author;
		this.createTime = createTime;
		this.collectCount = collectCount;
		this.content = content;
	}

	// Property accessors

	public Integer getAid() {
		return this.aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public String getArticleType() {
		return this.articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCollectCount() {
		return this.collectCount;
	}

	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSumary() {
		return sumary;
	}

	public void setSumary(String sumary) {
		this.sumary = sumary;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

}