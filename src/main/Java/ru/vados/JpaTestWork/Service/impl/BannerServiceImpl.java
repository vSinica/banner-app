package ru.vados.JpaTestWork.Service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.JpaTestWork.Dto.BannerDto;
import ru.vados.JpaTestWork.Entity.Banner;
import ru.vados.JpaTestWork.Entity.Category;
import ru.vados.JpaTestWork.Repository.BannerRepository;
import ru.vados.JpaTestWork.Repository.CategoryRepository;
import ru.vados.JpaTestWork.Service.BannerService;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
   
    private final BannerRepository bannerRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public String addBanner(BannerDto.BannerCreate bannerData) {

        Optional<Category> optCategory = categoryRepository.findByName(bannerData.getCategoryName());

        Category category;
        if(optCategory.isEmpty()) {
            return "No category with name: "+bannerData.getCategoryName();
        } else {
            category = optCategory.get();
        }

        Banner banner = new Banner();
        banner.setName(bannerData.getBannerName());
        banner.setContent(bannerData.getBannerText());
        banner.setPrice(bannerData.getPrice());
        banner.setDeleted(false);

        category.addBanner(banner);
        banner.setCategory(category);
        bannerRepository.save(banner);
        
        return null;
    }

    @Override
    @Transactional
    public String updateBanner(BannerDto.BannerUpdate bannerData){
        Long bannerId = bannerData.getId();

        Optional<Banner> optBanner = bannerRepository.findById(bannerId);
        Banner banner;
        if(optBanner.isEmpty()){
            return "No banner with id: " + bannerData.getId();
        } else {
            banner = optBanner.get();
        }

        if (!banner.getCategory().getName().equals(bannerData.getCategoryName())){

            Category oldCategory = banner.getCategory();
            oldCategory.removeBanner(banner);
            categoryRepository.save(oldCategory);

            Optional<Category> optNewCategory = categoryRepository.findByName(bannerData.getCategoryName());
            Category newCategory;
            if (optNewCategory.isEmpty()) {
                return "No category with name: " + bannerData.getCategoryName();
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

        return null;
    }

    @Override
    @Transactional
    public String deleteBanner(BannerDto.BannerDelete bannerData){

        Optional<Banner> optBanner = bannerRepository.findById(bannerData.getId());
        Banner banner;
        if(optBanner.isEmpty()){
            return "No banner with id: " + bannerData.getId();
        } else {
            banner = optBanner.get();
        }

        Optional<Category> optCategory = categoryRepository.findByName(banner.getCategory().getName());
        Category category;
        if(optCategory.isEmpty()){
            return "no category with name = "+ bannerData.getId();
        } else {
            category = optCategory.get();
        }

        category.removeBanner(banner);
        categoryRepository.save(category);

        banner.setCategory(null);
        banner.setDeleted(true);
        bannerRepository.save(banner);
        return  null;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Banner> findAllBanners(){
        return new ArrayList<>(bannerRepository.findBannerByDeletedIsFalse());
    }
}
