package ru.vados.JpaTestWork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vados.JpaTestWork.model.Banner;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.BannerRepository;
import ru.vados.JpaTestWork.repository.CategoryRepository;
import ru.vados.JpaTestWork.repository.RequestRepository;

import java.math.BigDecimal;
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
    @ResponseBody
    private String addBanner(@RequestBody(required = false) HashMap<String,Object> bannerData) throws JsonProcessingException {
        System.out.println("banner name " + (String) bannerData.get("bannerName"));
        System.out.println("banner text " + (String)bannerData.get("bannerText"));
        System.out.println("current category " + bannerData.get("currentCategory"));
        System.out.println("price " + new BigDecimal(bannerData.get("price").toString()));

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
        banner.setCategory_id(category);
        bannerRepository.save(banner);

        return null;
    }

    @CrossOrigin
    @PostMapping("/UpdateBanner")
    @ResponseBody
    private String updateBanner(@RequestBody(required = false)HashMap<String,Object> bannerData) throws JsonProcessingException {
        System.out.println("banner name " + (String) bannerData.get("bannerName"));
        System.out.println("banner text " + (String)bannerData.get("bannerText"));
        System.out.println("current category " + bannerData.get("currentCategory"));
        System.out.println("price " + new BigDecimal(bannerData.get("price").toString()));
        System.out.println("current id   " + bannerData.get("id"));

        Integer IntBannerId = (Integer) bannerData.get("id");
        Long bannerId = IntBannerId.longValue();

        Optional<Banner> banner = bannerRepository.findById(bannerId);
        if(!banner.isPresent()){
            System.out.println("banner is null");
            return objectMapper.writeValueAsString("No banner with id: " + bannerData.get("id"));
        }

        if (banner.get().getCategory_id().getName() != bannerData.get("currentCategory")) {

            Category oldcategory = banner.get().getCategory_id();
            oldcategory.removeBanner(banner.get());
            categoryRepository.save(oldcategory);

            Category newcategory = categoryRepository.findByName((String) bannerData.get("currentCategory"));
            if (newcategory == null) {
                System.out.println("new category is null");
                return objectMapper.writeValueAsString("No category with id: " + bannerData.get("currentCategory"));
            }

            newcategory.addBanner(banner.get());
            categoryRepository.save(newcategory);

            banner.get().setCategory_id(newcategory);
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
    @ResponseBody
    private String deleteBanner(@RequestBody(required = false)HashMap<String,Object> bannerData) throws JsonProcessingException {
        System.out.println("banner id on delete:  " + bannerData.get("bannerId"));
        System.out.println("category name of this banner:  " + bannerData.get("category_id"));

        Integer IntBannerId = (Integer) bannerData.get("bannerId");
        Long bannerId = IntBannerId.longValue();
        Optional<Banner> banner = bannerRepository.findById(bannerId);
        if(banner.get()==null){
            return  objectMapper.writeValueAsString("no banner with id = "+bannerData.get("bannerId"));
        }

        Category category = categoryRepository.findByName((String) bannerData.get("category_id"));
        if(category==null){
            return  objectMapper.writeValueAsString("no banner with name = "+ bannerData.get("category_id"));
        }

        for (Banner baner: category.getBanners()
        ) { System.out.println("Baner in category  "+ baner.getName());

        };

        category.removeBanner(banner.get());
        categoryRepository.save(category);

        banner.get().setCategory_id(null);
        banner.get().setDeleted(true);
        bannerRepository.save(banner.get());
        return  null;
    }

    @CrossOrigin
    @PostMapping("/GetCategoryNames")
    @ResponseBody
    private String getCategoryNames() throws JsonProcessingException {
        return objectMapper.writeValueAsString(categoryRepository.findAll());
    }


}
