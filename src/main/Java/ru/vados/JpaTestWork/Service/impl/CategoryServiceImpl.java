package ru.vados.JpaTestWork.Service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.JpaTestWork.Dto.CategoryDto;
import ru.vados.JpaTestWork.Entity.BannerEntity;
import ru.vados.JpaTestWork.Entity.CategoryEntity;
import ru.vados.JpaTestWork.Exception.HaveBannerInCategoryWhenDelete;
import ru.vados.JpaTestWork.Exception.NotFoundException;
import ru.vados.JpaTestWork.Repository.CategoryRepository;
import ru.vados.JpaTestWork.Service.CategoryService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ResponseEntity<Void> addCategory(@Valid CategoryDto.CategoryCreate newCategoryData){

        if(existsCategoryByName(newCategoryData.getCategoryName()))
            throw new NotFoundException("Category with " + newCategoryData.getCategoryName() + " name exist");

        CategoryEntity category = new CategoryEntity();

        if(newCategoryData.getCategoryName()== null || newCategoryData.getCategoryName().isBlank()) {
            throw new NotFoundException("Category name field is blank");
        }
        else category.setName(newCategoryData.getCategoryName());

        if(newCategoryData.getCategoryReqId()==null || newCategoryData.getCategoryReqId().isBlank()) {
           throw new NotFoundException("Request id category is blank");
        }
        else category.setReqName(newCategoryData.getCategoryReqId());

        category.setDeleted(false);
        categoryRepository.save(category);

        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteCategory(CategoryDto.CategoryUpdate categoryData) throws JsonProcessingException {

        Long idCategory = categoryData.getIdCategory();
        if(idCategory == null)
            throw new NotFoundException("id category is empty");

        if(!existsCategoryById(idCategory)){
            throw new NotFoundException("category with " + idCategory + " id not exist");
        }

        List<BannerEntity> banners = categoryRepository.findById(idCategory)
                .map(CategoryEntity::getBanners)
                .orElse(null);

        if(banners!=null && !banners.isEmpty())
        {
            StringBuilder messageToClient = new StringBuilder("You can't delete a category because it has banners. Here is their list: ");
            for (BannerEntity baner:banners) {
                messageToClient.append("  ");
                messageToClient.append(baner.getName());
            }
            throw new HaveBannerInCategoryWhenDelete(messageToClient.toString());
        }

        categoryRepository.deleteById(idCategory);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> updateCategory(CategoryDto.CategoryUpdate categoryData){
        Optional<CategoryEntity> optCategory = categoryRepository.findById(categoryData.getIdCategory());
        CategoryEntity category = null;
        if(optCategory.isEmpty()){
            throw new NotFoundException("not exist catogory with id:" + categoryData.getIdCategory());
        } else {
            category = optCategory.get();
        }

        if(categoryData.getCategoryName()==null || categoryData.getCategoryName().isBlank()) {
            throw new NotFoundException("name category field is blank");
        }
        else category.setName(categoryData.getCategoryName());


        if(categoryData.getCategoryReqId()==null || categoryData.getCategoryReqId().isBlank()) {
            throw new NotFoundException("Request id category field blank");
        }
        else category.setReqName(categoryData.getCategoryReqId());

        categoryRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Boolean existsCategoryByName(String categoryName) {
        return categoryRepository.existsCategoryByName(categoryName);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Iterable<CategoryDto.Categoryitem>> findAllCategory() {
        return  ResponseEntity.status(HttpStatus.OK).body(
                categoryRepository.findAllByDeletedFalse().stream().map(this::convert).collect(Collectors.toList())
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsCategoryById(Long idCategory) {
        return categoryRepository.existsCategoryById(idCategory);
    }

    private CategoryDto.Categoryitem convert(CategoryEntity category){
        return new CategoryDto.Categoryitem(
                category.getId(),
                category.getName(),
                category.getReqName(),
                category.getDeleted());
    }

}
