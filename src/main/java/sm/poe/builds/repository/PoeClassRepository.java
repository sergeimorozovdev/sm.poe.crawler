package sm.poe.builds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sm.poe.builds.entity.PoeClass;

public interface PoeClassRepository extends JpaRepository<PoeClass, String>
{
}
