package ua.home.orm.entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter @Setter
@ToString(callSuper = true , exclude = {"city"})
public class User extends BaseEntity{

	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "age")
	private int age;
	
	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = {
					CascadeType.DETACH,
					CascadeType.MERGE,
					CascadeType.PERSIST,
					CascadeType.REFRESH
			})
	@JoinColumn(name = "city_id")
	private City city = new City();
}
