package sm.poe.builds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sm.poe.builds.entity.Gem;

public interface GemRepository extends JpaRepository<Gem, String> {
}
