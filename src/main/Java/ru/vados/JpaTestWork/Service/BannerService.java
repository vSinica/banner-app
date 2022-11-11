package ru.vados.JpaTestWork.Service;

import org.springframework.http.ResponseEntity;
import ru.vados.JpaTestWork.Dto.BannerDto;
import ru.vados.JpaTestWork.Entity.BannerEntity;

import javax.validation.Valid;
import java.util.List;

public interface BannerService {
    ResponseEntity<Iterable<BannerDto.BannerItem>> findAllBanners();
    ResponseEntity<Void> addBanner(@Valid BannerDto.BannerCreate bannerData);
    ResponseEntity<Void> updateBanner(@Valid BannerDto.BannerUpdate bannerData);
    ResponseEntity<Void> deleteBanner(@Valid BannerDto.BannerDelete bannerData);
    ResponseEntity<List<String>> getAllCategoryNames();
}
