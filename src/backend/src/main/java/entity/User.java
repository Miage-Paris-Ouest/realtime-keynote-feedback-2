package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class User {
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="lastname")
	private String lastName;
	
}
