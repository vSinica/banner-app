package ru.vados.JpaTestWork.Service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.JpaTestWork.Dto.BannerDto;
import ru.vados.JpaTestWork.Entity.BannerEntity;
import ru.vados.JpaTestWork.Entity.CategoryEntity;
import ru.vados.JpaTestWork.Exception.NotFoundException;
import ru.vados.JpaTestWork.Repository.BannerRepository;
import ru.vados.JpaTestWork.Repository.CategoryRepository;
import ru.vados.JpaTestWork.Service.BannerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
   
    private final BannerRepository bannerRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ResponseEntity<Void> addBanner(BannerDto.BannerCreate bannerData) {

        Optional<CategoryEntity> optCategory = categoryRepository.findByName(bannerData.getCategoryName());

        CategoryEntity category;
        if(optCategory.isEmpty()) {
            throw new NotFoundException("No category with name: "+bannerData.getCategoryName());
        } else {
            category = optCategory.get();
        }

        BannerEntity banner = new BannerEntity();
        banner.setName(bannerData.getBannerName());
        banner.setContent(bannerData.getBannerText());
        banner.setPrice(bannerData.getPrice());
        banner.setDeleted(false);

        category.addBanner(banner);
        banner.setCategory(category);
        bannerRepository.save(banner);
        
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> updateBanner(BannerDto.BannerUpdate bannerData){
        Long bannerId = bannerData.getId();

        Optional<BannerEntity> optBanner = bannerRepository.findById(bannerId);
        BannerEntity banner;
        if(optBanner.isEmpty()){
            throw new NotFoundException("No banner with id: " + bannerData.getId());
        } else {
            banner = optBanner.get();
        }

        if (!banner.getCategory().getName().equals(bannerData.getCategoryName())){

            CategoryEntity oldCategory = banner.getCategory();
            oldCategory.removeBanner(banner);
            categoryRepository.save(oldCategory);

            Optional<CategoryEntity> optNewCategory = categoryRepository.findByName(bannerData.getCategoryName());
            CategoryEntity newCategory;
            if (optNewCategory.isEmpty()) {
                throw new NotFoundException("No category with name: " + bannerData.getCategoryName());
            } else {
                newCategory = optNewCategory.get();
            }

            newCategory.addBanner(banner);
            categoryRepository.save(newCategory);

            banner.setCategory(newCategory);
            bannerRepository.save(banner);
        }

        banner.setName(bannerData.getBannerName());
        banner.setContent(bannerData.getBannerText());
        banner.setPrice(bannerData.getPrice());
        banner.setDeleted(false);

        bannerRepository.save(banner);

        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteBanner(BannerDto.BannerDelete bannerData){

        Optional<BannerEntity> optBanner = bannerRepository.findById(bannerData.getId());
        BannerEntity banner;
        if(optBanner.isEmpty()){
            throw new NotFoundException("No banner with id: " + bannerData.getId());
        } else {
            banner = optBanner.get();
        }

        Optional<CategoryEntity> optCategory = categoryRepository.findByName(banner.getCategory().getName());
        CategoryEntity category;
        if(optCategory.isEmpty()){
            throw new NotFoundException("no category with name = "+ bannerData.getId());
        } else {
            category = optCategory.get();
        }

        category.removeBanner(banner);
        categoryRepository.save(category);

        banner.setCategory(null);
        banner.setDeleted(true);
        bannerRepository.save(banner);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Iterable<BannerDto.BannerItem>> findAllBanners(){
        return  ResponseEntity.status(HttpStatus.OK).body(
                bannerRepository.findBannerByDeletedIsFalse().stream().map(this::convert).collect(Collectors.toList())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<String>> getAllCategoryNames(){
        return  ResponseEntity.ok(
                categoryRepository.getAllCategoryNames()
        );
    }

    private BannerDto.BannerItem convert(BannerEntity banner){
        return new BannerDto.BannerItem(
                banner.getId(),
                banner.getName(),
                banner.getPrice(),
                banner.getContent(),
                banner.getDeleted()
        );
    }
}
