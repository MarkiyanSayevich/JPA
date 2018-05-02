package ua.home.orm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "country")
@NoArgsConstructor
@Getter @Setter
@ToString(callSuper = true, exclude = {"cities"})
public class Country extends BaseEntity{

	@Column(name ="name")
	private String name;
	
	@OneToMany(mappedBy = "country",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<City> cities = new ArrayList<>();
	
}
