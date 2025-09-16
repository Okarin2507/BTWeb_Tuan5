package vn.week3.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

@Entity
@Table(name = "Users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(name = "fullname", length = 100)
	private String fullname;

	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "image")
	private String image;

	@Column(nullable = false)
	private int roleId;

	@Column(name = "createdDate")
	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Category> categories = new ArrayList<>();

	public void addCategory(Category category) {
		this.categories.add(category);
		category.setUser(this);
	}

	public void removeCategory(Category category) {
		this.categories.remove(category);
		category.setUser(null);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
