package app.igesa.metiers.implement;
import app.igesa.dto.CategoryDTO;
import app.igesa.entity.Category;
import app.igesa.enumerations.ErrorCode;
import app.igesa.exceptions.ResourceNotFoundException;
import app.igesa.metiers.Icategory;
import app.igesa.metiers.Ientreprise;
import app.igesa.repository.IcategoryRepository;
import app.igesa.upload.FilesStorageService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author  Wassim Hachaani
 *
 */
@Service
@AllArgsConstructor
@NoArgsConstructor
public class CategoryImp  implements Icategory {

    @Autowired
    IcategoryRepository icategoryRepository;

    @Autowired
    Ientreprise ientrepriseService;
    @Autowired
    FilesStorageService filesStorageService;
    private static final Logger log = LoggerFactory.getLogger(CategoryImp.class);

    @Override
    public CategoryDTO save(CategoryDTO c) {
        Category category= new Category();
        if (c.getId()!=null){
            category = icategoryRepository.findById(c.getId()).orElseThrow(IllegalAccessError::new);
        }
        category.setEnterprise(ientrepriseService.getCurrentEnterprise());
        Category saved = icategoryRepository.save(CategoryDTO.toEntity(c));
        return CategoryDTO.fromEntity(saved);

    }


    @Override
    public Collection<CategoryDTO> getAllByEntreprise() {
        return icategoryRepository.findFirstByEntrepriseId(ientrepriseService.getCurrentEnterprise().getId()).stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public CategoryDTO findById(Long id) {
        if (id == null) {
            log.error(" Category Id is NULL .. !!");
            return null;
        }

        return icategoryRepository.findById(id).map(CategoryDTO::fromEntity).orElseThrow(() ->
                new ResourceNotFoundException(" No Category with  Id = :: " + id + " was founded {} ..!",
                        ErrorCode.CATEGORY_NOT_FOUND));
    }
        @Transactional
        @Override
        public void delete(Long id) {
        Optional<Category> optionalCategory = icategoryRepository.findById(id);
        if (!optionalCategory.isPresent()) throw new ResourceNotFoundException("Category " + id + " not exist");
        icategoryRepository.deleteById(id);
    }

    @Override
    public void deleteImage(Long categoryId, String type) {
        Category category = icategoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category " + categoryId + " not exist"));
        Long eid = category.getEnterprise().getId();
        Long gid = category.getEnterprise().getGroupe().getId();
        String name;
        if (type.equalsIgnoreCase("MENU")) {
            name = category.getMenuimage().substring(category.getMenuimage().indexOf("/category/") + "/category/".length(), category.getMenuimage().indexOf("?enterpriseId"));
            category.setMenuimage(null);
        }
        else {
            name = category.getBannerimage().substring(category.getBannerimage().indexOf("/category/") + "/category/".length(), category.getBannerimage().indexOf("?enterpriseId"));
            category.setBannerimage(null);
        }
        //filesStorageService.deleteImage(ImageTypes.CATEGORY, name, gid, eid);
        icategoryRepository.save(category);
    }


    @Override
    public CategoryDTO update(CategoryDTO c, Long id) {
        return null;
    }
}
