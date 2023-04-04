package sm.poe.builds.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "build")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Build
{
	@Id
	@Column(name = "url")
	private String url;
	
	@Column(name = "name")
	private String name;
	@Column(name = "version")
	private String version;
	
	@Column(name = "views")
	private Integer views;
	
	@Column(name = "author")
	private String author;
	
	@ManyToOne
	@JoinColumn(name = "poe_class", nullable = false)
	private PoeClass poeClass;
	
	@Override
	public String toString()
	{
		return "Build{" +
				" version='" + version + '\'' +
				", url='" + url + '\'' +
				", name='" + name + '\'' +
				", views=" + views +
				", author=" + author +
				", poeClass=" + poeClass.toString() +
				'}';
	}
}
