package ru.vados.BannerApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vados.BannerApp.dto.BannerDto;
import ru.vados.BannerApp.service.BannerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @PostMapping("/AddBanner")
    public ResponseEntity<Void> addBanner(@RequestBody BannerDto.BannerCreate bannerData) {
        return bannerService.addBanner(bannerData);
    }

    @PostMapping("/UpdateBanner")
    public ResponseEntity<Void> updateBanner(@RequestBody BannerDto.BannerUpdate bannerData) {
        return bannerService.updateBanner(bannerData);
    }

    @PostMapping("/DeleteBanner")
    public ResponseEntity<Void> deleteBanner(@RequestBody BannerDto.BannerDelete bannerData) {
        return bannerService.deleteBanner(bannerData);
    }

    @PostMapping("/GetCategoryNames")
    public ResponseEntity<List<String>> getCategoryNames() throws JsonProcessingException {
        return bannerService.getAllCategoryNames();
    }

    @PostMapping("/GetBanners")
    public ResponseEntity<Iterable<BannerDto.BannerItem>> getBanners() throws JsonProcessingException {
        return bannerService.findAllBanners();
    }
}
