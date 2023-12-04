package sm.poe.builds.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "poe_class")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PoeClass
{
	@Id
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "poeClass")
	private Set<Build> builds;
}
