package ru.vados.JpaTestWork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vados.JpaTestWork.DTO.BannerDto;
import ru.vados.JpaTestWork.model.Banner;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.BannerRepository;
import ru.vados.JpaTestWork.service.BannerService;
import ru.vados.JpaTestWork.service.CategoryService;

import java.util.Optional;

@Service
public class BannerServiceImpl implements BannerService {
   
    BannerRepository bannerRepository;
    CategoryService categoryService;
    
    @Autowired
    public BannerServiceImpl(BannerRepository bannerRepository, CategoryService categoryService) {
        this.bannerRepository = bannerRepository;
        this.categoryService = categoryService;
    }
    
    
    public String addBanner(BannerDto bannerData) {
        Category category = categoryService.findByCategoryName(bannerData.getCurrentCategory());
        if(category==null) {
            return "No category with name: "+bannerData.getCurrentCategory();
        }

        Banner banner = new Banner();
        banner.setName(bannerData.getBannerName());
        banner.setContent(bannerData.getBannerText());
        banner.setPrice(bannerData.getPrice());
        banner.setDeleted(false);

        category.addBanner(banner);
        banner.setCategoryId(category);
        bannerRepository.save(banner);
        
        return null;
    }

    public String updateBanner(BannerDto bannerData){
        Long bannerId = bannerData.getId();

        Optional<Banner> banner = bannerRepository.findById(bannerId);
        if(!banner.isPresent()){
            return "No banner with id: " + bannerData.getId();
        }

        if (banner.get().getCategoryId().getName().equals(bannerData.getCurrentCategory())){

            Category oldCategory = banner.get().getCategoryId();
            oldCategory.removeBanner(banner.get());
            categoryService.saveCategory(oldCategory);

            Category newCategory = categoryService.findByCategoryName(bannerData.getCurrentCategory());
            if (newCategory == null) {
                System.out.println("new category is null");
                return "No category with id: " + bannerData.getCurrentCategory();
            }

            newCategory.addBanner(banner.get());
            categoryService.saveCategory(newCategory);

            banner.get().setCategoryId(newCategory);
            bannerRepository.save(banner.get());
        }

        banner.get().setName(bannerData.getBannerName());
        banner.get().setContent(bannerData.getBannerText());
        banner.get().setPrice(bannerData.getPrice());
        banner.get().setDeleted(false);

        bannerRepository.save(banner.get());

        return null;
    }

    public String deleteBanner(BannerDto bannerData){
        Long bannerId = bannerData.getBannerId();
        Optional<Banner> banner = bannerRepository.findById(bannerId);
        if(!banner.isPresent()){
            return  "no banner with id = "+bannerData.getBannerId();
        }

        Category category = categoryService.findByCategoryName(bannerData.getCategory_id());
        if(category==null){
            return "no category with name = "+ bannerData.getCategory_id();
        }

        category.removeBanner(banner.get());
        categoryService.saveCategory(category);

        banner.get().setCategoryId(null);
        banner.get().setDeleted(true);
        bannerRepository.save(banner.get());
        return  null;
    }

    public void saveBanner(Banner banner) {
        bannerRepository.save(banner);
    }

    public Optional<Banner> findBannerById(Long bannerId){
        return bannerRepository.findById(bannerId);
    }

    public Iterable<Banner> findAllBanners(){
        return bannerRepository.findAll();
    }
}
