package ru.vados.JpaTestWork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin
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

    @PostMapping("/AddBanner")
    @Transactional
    public ResponseEntity<String> addBanner(@RequestBody BannerDto bannerData) {
        return ResponseEntity.status(HttpStatus.OK).
                body(bannerService.addBanner(bannerData));
    }

    @PostMapping("/UpdateBanner")
    @Transactional
    public ResponseEntity<String> updateBanner(@RequestBody BannerDto bannerData) {
        return ResponseEntity.status(HttpStatus.OK).
                body(bannerService.updateBanner(bannerData));
    }

    @PostMapping("/DeleteBanner")
    @Transactional
    public ResponseEntity<String> deleteBanner(@RequestBody BannerDto bannerData) {
        return ResponseEntity.status(HttpStatus.OK).
                body(bannerService.deleteBanner(bannerData));
    }

    @PostMapping("/GetCategoryNames")
    public ResponseEntity<String> getCategoryNames() throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).
                body(objectMapper.writeValueAsString(categoryService.getAllCategoryNames()));
    }

    @Transactional
    @PostMapping("/GetBanners")
    public ResponseEntity<String> getBanners() throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).
                body(objectMapper.writeValueAsString(bannerService.findAllBanners()));
    }
}
