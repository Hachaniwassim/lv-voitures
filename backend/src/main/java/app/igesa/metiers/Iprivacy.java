package app.igesa.metiers;
import app.igesa.dto.PrivacyDTO;
import java.util.Collection;
import java.util.Optional;

public interface Iprivacy {
    public PrivacyDTO updateByEntreprise (PrivacyDTO p);

    Collection<PrivacyDTO> FindByEntrepriseId(Long id_entreprise);

    public PrivacyDTO  findById(Long id_entreprise);
    public void delete(Long id);

}
