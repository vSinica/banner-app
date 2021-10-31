package ru.vados.JpaTestWork.service;

import ru.vados.JpaTestWork.DTO.BannerDto;
import ru.vados.JpaTestWork.model.Banner;

import java.util.Optional;

public interface BannerService {
    void saveBanner(Banner banner);
    Optional<Banner> findBannerById(Long bannerId);
    Iterable<Banner> findAllBanners();
    String addBanner(BannerDto bannerData);
    String updateBanner(BannerDto bannerData);
    String deleteBanner(BannerDto bannerData);
}
