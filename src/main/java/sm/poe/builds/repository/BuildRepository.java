package sm.poe.builds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sm.poe.builds.entity.Build;

import java.util.List;

public interface BuildRepository extends JpaRepository<Build, String> {
    List<Build> findByPoeClassNameAndVersion(String poeClassName, String version);

    List<Build> findByVersion(String version);

    List<Build> findByPoeClassName(String poeClassName);
}
