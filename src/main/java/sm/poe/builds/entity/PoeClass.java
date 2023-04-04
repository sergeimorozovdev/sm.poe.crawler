package sm.poe.builds.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "poe_class")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PoeClass
{
	@Id
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "poeClass")
	private Set<Build> builds;
	
	
	@Override
	public String toString()
	{
		return "PoeClass{" +
				"name='" + name + '\'' +
				'}';
	}
}
