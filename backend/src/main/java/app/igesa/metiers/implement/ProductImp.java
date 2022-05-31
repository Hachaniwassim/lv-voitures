package app.igesa.metiers.implement;

import app.igesa.dto.*;
import app.igesa.entity.*;
import app.igesa.enumerations.ErrorCode;
import app.igesa.exceptions.InvalideEntityException;
import app.igesa.exceptions.ResourceNotFoundException;
import app.igesa.metiers.Iproduct;
import app.igesa.repository.IcategoryRepository;
import app.igesa.repository.IproductRepository;
import app.igesa.validators.ProductValidators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wassim Hachani
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Service
public class ProductImp  implements Iproduct {

    @Autowired
    IproductRepository iproductRepository;
    @Autowired
    IcategoryRepository icategoryRepository;
    private static final Logger log = LoggerFactory.getLogger(ProductImp.class);


    @Override
    public ProductDTO save(ProductDTO p) {

        Product saved =iproductRepository.save(ProductDTO.toEntity(p));
        return ProductDTO.fromEntity(saved);

    }


    @Override
    public Collection<ProductDTO> view( Long enterprise_id) {
        return iproductRepository.findByEntrepriseId(enterprise_id).stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) {
        if ( id == null) {
            log.error(" Post Id is NULL .. !!");
            return null ;
        }

        return  iproductRepository.findById(id).map(ProductDTO::fromEntity).orElseThrow(()->
                new ResourceNotFoundException(" No Post with  Id = :: " +id+ " was founded {} ..!",
                        ErrorCode.PRODUCT_NOT_FOUND));

    }

    @Override
    public void delete(Long id) {
        if ( id == null) {
            log.error(" Product Id is NULL .. !!");
            return  ;
        }
      iproductRepository.deleteById(id);
    }
    @Override
    public void assignTags(Long product_id,Long tag_id){
        iproductRepository.assignTags(product_id,tag_id);
    }
}
