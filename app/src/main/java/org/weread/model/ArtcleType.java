package org.weread.model;

/**
 * ArtcleType entity. @author MyEclipse Persistence Tools
 */

public class ArtcleType implements java.io.Serializable {

	// Fields

	private String type;
	private String typeUrl;
	private Integer typeCount;

	// Constructors

	/** default constructor */
	public ArtcleType() {
	}

	/** full constructor */
	public ArtcleType(String typeUrl, Integer typeCount) {
		this.typeUrl = typeUrl;
		this.typeCount = typeCount;
	}

	// Property accessors

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeUrl() {
		return this.typeUrl;
	}

	public void setTypeUrl(String typeUrl) {
		this.typeUrl = typeUrl;
	}

	public Integer getTypeCount() {
		return this.typeCount;
	}

	public void setTypeCount(Integer typeCount) {
		this.typeCount = typeCount;
	}

}