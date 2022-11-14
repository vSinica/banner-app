package ru.vados.BannerApp.Service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.BannerApp.Dto.CategoryDto;
import ru.vados.BannerApp.Entity.BannerEntity;
import ru.vados.BannerApp.Entity.CategoryEntity;
import ru.vados.BannerApp.Exception.HaveBannerInCategoryWhenDelete;
import ru.vados.BannerApp.Exception.NotFoundException;
import ru.vados.BannerApp.Repository.CategoryRepository;
import ru.vados.BannerApp.Service.CategoryService;

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

        category.setName(categoryData.getCategoryName());
        category.setReqName(categoryData.getCategoryReqId());

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
