package app.igesa.metiers.implement;
import app.igesa.dto.CookiesDTO;
import app.igesa.entity.Cookies;
import app.igesa.entity.Entreprise;
import app.igesa.exceptions.ResourceNotFoundException;
import app.igesa.metiers.Icookies;
import app.igesa.metiers.Ientreprise;
import app.igesa.repository.CookiesRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Tarchoun Abir
 *
 */

@Service
@NoArgsConstructor
@AllArgsConstructor
public class CookiesImp implements Icookies {

    private static final Logger log = LoggerFactory.getLogger(CookiesImp.class);

    @Autowired
    Ientreprise ientrepriseService;
    @Autowired
    CookiesRepository cookiesRepository;

    @Override
    public CookiesDTO updateCookies(CookiesDTO cookiesDto, Long id_entreprise) {
        Cookies saved = cookiesRepository.save(CookiesDTO.toEntity(cookiesDto));
        return CookiesDTO.fromEntity(saved);

    }

    @Override
    public CookiesDTO findByEntrepriseId(Long id_entreprise) {
        log.debug("HTTP GET BY ID {} ..", id_entreprise);
        if (id_entreprise== null) {
            log.error(" cookies Id is NULL .. !!");
            return null;
        }

        return (CookiesDTO) cookiesRepository.findByEntrepriseId(id_entreprise).stream().map(CookiesDTO::fromEntity);
    }


    @Override
    public Collection<CookiesDTO> findCookiesByEntrepriseId(Long id_entreprise) {

        return cookiesRepository.findByEntrepriseId(id_entreprise).stream()
                .map(CookiesDTO::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public CookiesDTO findById(Long id) {
        log.debug("HTTP GET BY ID {} ..", id);
        if (id == null) {
            log.error(" cookies Id is NULL .. !!");
            return null;
        }

        return cookiesRepository.findById(id).map(CookiesDTO::fromEntity).orElseThrow(() ->
                new ResourceNotFoundException(" No cookies   with  Id = :: " + id + " was founded {} ..!"));

    }

}






