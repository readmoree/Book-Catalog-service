package com.readmoree.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="publishers")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Publisher extends BaseEntity {

	@Column(length=40, nullable=false)
	private String name;
	
	@Column(length=100, nullable = false, unique = true)
	private String email;
	
	@Column(length=20)
	private String mobileNo;
}
