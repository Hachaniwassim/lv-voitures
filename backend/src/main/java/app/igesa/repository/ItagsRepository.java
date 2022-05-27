package app.igesa.repository;
import app.igesa.entity.Cookies;
import app.igesa.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;


/**
 * @author Tarchoun.Abir
 */

public interface ItagsRepository extends JpaRepository<Tags,Long> , JpaSpecificationExecutor<Tags> {
    Optional<Tags> findFirstByEntrepriseId(Long id);
    //List<Tags> findByEntrepriseId(Long id);
}
