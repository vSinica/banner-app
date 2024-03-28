package ru.vados.BannerApp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.BannerApp.dto.CategoryDto;
import ru.vados.BannerApp.entity.BannerEntity;
import ru.vados.BannerApp.entity.CategoryEntity;
import ru.vados.BannerApp.exception.ExistException;
import ru.vados.BannerApp.exception.HaveBannerInCategoryWhenDelete;
import ru.vados.BannerApp.repository.CategoryRepository;
import ru.vados.BannerApp.service.CategoryService;

import javax.validation.Valid;
import java.util.List;
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
            throw new ExistException("Category with " + newCategoryData.getCategoryName() + " name exist");

        if(existsCategoryByReqName(newCategoryData.getCategoryReqId()))
            throw new ExistException("Category with " + newCategoryData.getCategoryReqId() + " Req name exist");

        CategoryEntity category = new CategoryEntity();
        category.setName(newCategoryData.getCategoryName());
        category.setReqName(newCategoryData.getCategoryReqId());

        category.setDeleted(false);
        categoryRepository.save(category);

        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteCategory(CategoryDto.CategoryDelete categoryData) throws JsonProcessingException {

        Long idCategory = categoryData.getIdCategory();
        if(!existsCategoryById(idCategory)){
            throw new ExistException("category with " + idCategory + " id not exist");
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
        CategoryEntity category = categoryRepository.findById(categoryData.getIdCategory())
                .orElseThrow(() -> new ExistException("not exist catogory with id:" + categoryData.getIdCategory()));

        category.setName(categoryData.getCategoryName());
        category.setReqName(categoryData.getCategoryReqId());

        categoryRepository.save(category);

        return ResponseEntity.noContent().build();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Boolean existsCategoryByName(String categoryName) {
        return categoryRepository.existsCategoryEntityByName(categoryName);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsCategoryByReqName(String categoryReqName) {
        return categoryRepository.existsCategoryEntityByReqName(categoryReqName);
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
        return categoryRepository.existsCategoryEntityById(idCategory);
    }

    private CategoryDto.Categoryitem convert(CategoryEntity category){
        return new CategoryDto.Categoryitem(
                category.getId(),
                category.getName(),
                category.getReqName(),
                category.getDeleted());
    }

}
