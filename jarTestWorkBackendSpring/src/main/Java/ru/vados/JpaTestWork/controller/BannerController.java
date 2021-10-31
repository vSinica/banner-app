package ru.vados.JpaTestWork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vados.JpaTestWork.DTO.BannerDto;
import ru.vados.JpaTestWork.model.Banner;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.RequestRepository;
import ru.vados.JpaTestWork.service.BannerService;
import ru.vados.JpaTestWork.service.CategoryService;

import java.util.Optional;

@RestController
public class BannerController {

    CategoryService categoryService;
    BannerService bannerService;

    RequestRepository requestRepository;

    @Autowired
    public BannerController(CategoryService categoryService, BannerService bannerService, RequestRepository requestRepository) {
        this.categoryService = categoryService;
        this.bannerService = bannerService;
        this.requestRepository = requestRepository;
    }


    ObjectMapper objectMapper = new ObjectMapper();

    @CrossOrigin
    @PostMapping("/AddBanner")
    @Transactional
    public String addBanner(@RequestBody BannerDto bannerData) throws JsonProcessingException {
        return bannerService.addBanner(bannerData);
    }

    @CrossOrigin
    @PostMapping("/UpdateBanner")
    @Transactional
    public String updateBanner(@RequestBody BannerDto bannerData) throws JsonProcessingException {
        return bannerService.updateBanner(bannerData);
    }

    @CrossOrigin
    @PostMapping("/DeleteBanner")
    @Transactional
    public String deleteBanner(@RequestBody BannerDto bannerData) throws JsonProcessingException {
        return bannerService.deleteBanner(bannerData);
    }

    @CrossOrigin
    @PostMapping("/GetCategoryNames")
    public String getCategoryNames() throws JsonProcessingException {
        return objectMapper.writeValueAsString(categoryService.getAllCategoryNames());
    }

    @CrossOrigin
    @Transactional
    @PostMapping("/GetBanners")
    public String getBanners() throws JsonProcessingException {
        return objectMapper.writeValueAsString(bannerService.findAllBanners());
    }
}
