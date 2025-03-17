package com.ecommerce.security.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "user_name",unique = true , nullable = false)
	@NotNull(message = "UseName cannot be Empty")
	@Size(min = 6, max = 10, message = "UserName should be in between 6 to 10 character")
	private String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(nullable = false)
	@NotNull(message="Password cannot be Empty")
	//@Size(min = 6, max = 8 ,message = "password should contain A-Z , a-z , atleast one Numeric")
	private String password;
	
	@Column(nullable = false)
	private String role;
	
	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", userName=" + username + ", password=" + password + ", role=" + role + "]";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
