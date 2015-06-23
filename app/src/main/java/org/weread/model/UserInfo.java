package org.weread.model;

/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */

public class UserInfo implements java.io.Serializable {

	// Fields

	private Integer uid;
	private Users users;
	private String gender;
	private String addr;
	private Integer old;
	private String thumb;
	private String userBg;

	// Constructors

	/** default constructor */
	public UserInfo() {
	}

	/** minimal constructor */
	public UserInfo(Users users, String gender, String addr, Integer old) {
		this.users = users;
		this.gender = gender;
		this.addr = addr;
		this.old = old;
	}

	/** full constructor */
	public UserInfo(Users users, String gender, String addr, Integer old,
			String thumb, String userBg) {
		this.users = users;
		this.gender = gender;
		this.addr = addr;
		this.old = old;
		this.thumb = thumb;
		this.userBg = userBg;
	}

	// Property accessors

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getOld() {
		return this.old;
	}

	public void setOld(Integer old) {
		this.old = old;
	}

	public String getThumb() {
		return this.thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getUserBg() {
		return this.userBg;
	}

	public void setUserBg(String userBg) {
		this.userBg = userBg;
	}

}