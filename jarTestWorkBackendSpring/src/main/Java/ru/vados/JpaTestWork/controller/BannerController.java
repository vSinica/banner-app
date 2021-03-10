package ru.vados.JpaTestWork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.vados.JpaTestWork.model.Banner;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.BannerRepository;
import ru.vados.JpaTestWork.repository.CategoryRepository;
import ru.vados.JpaTestWork.repository.RequestRepository;

import java.util.HashMap;
import java.util.Optional;

@RestController
public class BannerController {

    CategoryRepository categoryRepository;

    BannerRepository bannerRepository;

    RequestRepository requestRepository;

    @Autowired
    public BannerController(CategoryRepository categoryRepository,
                          BannerRepository bannerRepository,
                          RequestRepository requestRepository) {
        this.categoryRepository = categoryRepository;
        this.bannerRepository = bannerRepository;
        this.requestRepository = requestRepository;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @CrossOrigin
    @PostMapping("/AddBanner")
    @Transactional
    public String addBanner(@RequestBody(required = false) HashMap<String,Object> bannerData) throws JsonProcessingException {

        Category category = categoryRepository.findByName((String) bannerData.get("currentCategory"));
        if(category==null) {
            return objectMapper.writeValueAsString("No category with name: "+bannerData.get("currentCategory"));
        }

        Banner banner = new Banner();
        banner.setName((String) bannerData.get("bannerName"));
        banner.setContent((String) bannerData.get("bannerText"));
        banner.setPrice(Float.parseFloat(String.valueOf(bannerData.get("price"))));
        banner.setDeleted(false);

        category.addBanner(banner);
        banner.setCategoryId(category);
        bannerRepository.save(banner);

        return null;
    }

    @CrossOrigin
    @PostMapping("/UpdateBanner")
    @Transactional
    public String updateBanner(@RequestBody(required = false)HashMap<String,Object> bannerData) throws JsonProcessingException {

        Integer IntBannerId = (Integer) bannerData.get("id");
        Long bannerId = IntBannerId.longValue();

        Optional<Banner> banner = bannerRepository.findById(bannerId);
        if(!banner.isPresent()){
            System.out.println("banner is null");
            return objectMapper.writeValueAsString("No banner with id: " + bannerData.get("id"));
        }

        if (banner.get().getCategoryId().getName().equals(bannerData.get("currentCategory"))) {

            Category oldcategory = banner.get().getCategoryId();
            oldcategory.removeBanner(banner.get());
            categoryRepository.save(oldcategory);

            Category newcategory = categoryRepository.findByName((String) bannerData.get("currentCategory"));
            if (newcategory == null) {
                System.out.println("new category is null");
                return objectMapper.writeValueAsString("No category with id: " + bannerData.get("currentCategory"));
            }

            newcategory.addBanner(banner.get());
            categoryRepository.save(newcategory);

            banner.get().setCategoryId(newcategory);
            bannerRepository.save(banner.get());
        }

        banner.get().setName((String) bannerData.get("bannerName"));
        banner.get().setContent((String) bannerData.get("bannerText"));
        banner.get().setPrice(Float.parseFloat(String.valueOf(bannerData.get("price"))));
        banner.get().setDeleted(false);

        bannerRepository.save(banner.get());

        return null;
    }

    @CrossOrigin
    @PostMapping("/DeleteBanner")
    @Transactional
    public String deleteBanner(@RequestBody(required = false)HashMap<String,Object> bannerData) throws JsonProcessingException {

        Integer IntBannerId = (Integer) bannerData.get("bannerId");
        Long bannerId = IntBannerId.longValue();
        Optional<Banner> banner = bannerRepository.findById(bannerId);
        if(!banner.isPresent()){
            return  objectMapper.writeValueAsString("no banner with id = "+bannerData.get("bannerId"));
        }

        Category category = categoryRepository.findByName((String) bannerData.get("category_id"));
        if(category==null){
            return  objectMapper.writeValueAsString("no banner with name = "+ bannerData.get("category_id"));
        }

        category.removeBanner(banner.get());
        categoryRepository.save(category);

        banner.get().setCategoryId(null);
        banner.get().setDeleted(true);
        bannerRepository.save(banner.get());
        return  null;
    }

    @CrossOrigin
    @PostMapping("/GetCategoryNames")
    public String getCategoryNames() throws JsonProcessingException {
        return objectMapper.writeValueAsString(categoryRepository.findAllByName());
    }
}
