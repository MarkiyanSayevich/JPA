package ua.home.orm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "city")
@NoArgsConstructor
@Getter @Setter
@ToString(callSuper = true, exclude = {"country","users"})
public class City extends BaseEntity {

	@Column(name = "name")
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER,
			cascade = {
			CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH
			})
	@JoinColumn(name = "country_id")
	private Country country = new Country();
	
	@OneToMany(
			mappedBy = "city",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private List<User> users = new ArrayList<>();
}
