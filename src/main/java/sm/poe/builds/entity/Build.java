package sm.poe.builds.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "build")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
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
}
