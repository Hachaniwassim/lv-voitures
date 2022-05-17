package app.igesa.dto;

import app.igesa.entity.Cookies;
import app.igesa.entity.Entreprise;
import lombok.Builder;
import lombok.Data;
import javax.persistence.Column;
import java.util.Date;

/**
 * @author Tarchoun Abir
 */

@Builder
@Data
public class CookiesDTO {

    private Long id ;
    private String title ;
    private String htmlContent;
    private Long entrepriseId;
    protected Date createdDate;
    protected Date lastModifiedDate;

    public static CookiesDTO fromEntity(Cookies cookie) {

        return CookiesDTO.builder()
                .id(cookie.getId())
                .htmlContent(cookie.getHtmlContent())
                .title(cookie.getTitle())
                .lastModifiedDate(cookie.getLastModifiedDate())
                .entrepriseId(cookie.getEntreprise().getId())
                .createdDate(cookie.getCreatedDate())
                .build();
    }

    public static Cookies toEntity(CookiesDTO dto) {

        Cookies cookie = new Cookies();
        cookie.setId(dto.getId());
        cookie.setHtmlContent(dto.getHtmlContent());
        cookie.setTitle(dto.getTitle());
        cookie.setCreatedDate(dto.getCreatedDate());
        cookie.setLastModifiedDate(dto.getLastModifiedDate());
        //Entreprise entreprise = new Entreprise();
        //entreprise.setId(dto.getEntrepriseId());
        //cookie.setEntreprise(entreprise);
        return cookie;
    }


}