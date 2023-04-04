package sm.poe.builds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sm.poe.builds.entity.Build;

public interface BuildRepository extends JpaRepository<Build, String>
{
}
