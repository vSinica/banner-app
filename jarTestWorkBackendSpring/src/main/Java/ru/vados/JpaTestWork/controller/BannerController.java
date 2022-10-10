package ru.vados.JpaTestWork.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vados.JpaTestWork.Dto.BannerDto;
import ru.vados.JpaTestWork.Repository.RequestRepository;
import ru.vados.JpaTestWork.Service.BannerService;
import ru.vados.JpaTestWork.Service.CategoryService;

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
    public ResponseEntity<String> addBanner(@RequestBody BannerDto.BannerCreate bannerData) {
        return ResponseEntity.status(HttpStatus.OK).
                body(bannerService.addBanner(bannerData));
    }

    @PostMapping("/UpdateBanner")
    public ResponseEntity<String> updateBanner(@RequestBody BannerDto.BannerUpdate bannerData) {
        return ResponseEntity.status(HttpStatus.OK).
                body(bannerService.updateBanner(bannerData));
    }

    @PostMapping("/DeleteBanner")
    public ResponseEntity<String> deleteBanner(@RequestBody BannerDto.BannerDelete bannerData) {
        return ResponseEntity.status(HttpStatus.OK).
                body(bannerService.deleteBanner(bannerData));
    }

    @PostMapping("/GetCategoryNames")
    public ResponseEntity<String> getCategoryNames() throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).
                body(objectMapper.writeValueAsString(categoryService.getAllCategoryNames()));
    }

    @PostMapping("/GetBanners")
    public ResponseEntity<String> getBanners() throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).
                body(objectMapper.writeValueAsString(bannerService.findAllBanners()));
    }
}
