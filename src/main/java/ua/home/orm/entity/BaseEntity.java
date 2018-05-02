package ua.home.orm.entity;

import javax.persistence.*;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
}
