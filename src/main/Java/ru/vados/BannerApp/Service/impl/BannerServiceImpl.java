package ru.vados.BannerApp.Service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.BannerApp.Dto.BannerDto;
import ru.vados.BannerApp.Entity.BannerEntity;
import ru.vados.BannerApp.Entity.CategoryEntity;
import ru.vados.BannerApp.Exception.NotFoundException;
import ru.vados.BannerApp.Repository.BannerRepository;
import ru.vados.BannerApp.Repository.CategoryRepository;
import ru.vados.BannerApp.Service.BannerService;

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

        CategoryEntity category = categoryRepository.findByName(bannerData.getCategoryName())
                .orElseThrow(() -> new NotFoundException("No category with name: " + bannerData.getCategoryName()));

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

        BannerEntity banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new NotFoundException("No banner with id: " + bannerData.getId()));

        if (!banner.getCategory().getName().equals(bannerData.getCategoryName())){

            CategoryEntity oldCategory = banner.getCategory();
            oldCategory.removeBanner(banner);
            categoryRepository.save(oldCategory);

            CategoryEntity newCategory = categoryRepository.findByName(bannerData.getCategoryName())
                    .orElseThrow(() -> new NotFoundException("No category with name: " + bannerData.getCategoryName()));

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

        BannerEntity banner = bannerRepository.findById(bannerData.getId())
                .orElseThrow(() ->  new NotFoundException("No banner with id: " + bannerData.getId()));

        CategoryEntity category = categoryRepository.findByName(banner.getCategory().getName())
                .orElseThrow(() -> new NotFoundException("no category with name = "+ bannerData.getId()));

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
