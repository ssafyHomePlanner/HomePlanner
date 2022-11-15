package com.ssafy.homepjt.model.dto;

public class MemberDto {
	private String id;
	private String pw;
	private String name;
	private String email;
	private int age;
	private String phone;
	private char gender;
	private String joinDate;
	private String salt;

	public MemberDto() {
		super();
	}

	public MemberDto(String id, String pw, String name, String email, int age, String phone, char gender,
			String joinDate, String salt) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.age = age;
		this.phone = phone;
		this.gender = gender;
		this.joinDate = joinDate;
		this.salt = salt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", pw=" + pw + ", name=" + name + ", email=" + email + ", age=" + age
				+ ", phone=" + phone + ", gender=" + gender + ", joinDate=" + joinDate + ", salt=" + salt + "]";
	}

}
