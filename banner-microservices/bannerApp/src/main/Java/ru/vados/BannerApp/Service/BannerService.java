package ru.vados.BannerApp.Service;

import org.springframework.http.ResponseEntity;
import ru.vados.BannerApp.Dto.BannerDto;

import javax.validation.Valid;
import java.util.List;

public interface BannerService {
    ResponseEntity<Iterable<BannerDto.BannerItem>> findAllBanners();
    ResponseEntity<Void> addBanner(@Valid BannerDto.BannerCreate bannerData);
    ResponseEntity<Void> updateBanner(@Valid BannerDto.BannerUpdate bannerData);
    ResponseEntity<Void> deleteBanner(@Valid BannerDto.BannerDelete bannerData);
    ResponseEntity<List<String>> getAllCategoryNames();
}
