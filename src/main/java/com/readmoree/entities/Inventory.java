package com.readmoree.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="inventory")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Inventory extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name="book_id")
	private Book bookId;
	
	
	private long quantity;

}
