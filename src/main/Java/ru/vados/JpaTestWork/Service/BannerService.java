package ru.vados.JpaTestWork.Service;

import ru.vados.JpaTestWork.Dto.BannerDto;
import ru.vados.JpaTestWork.Entity.Banner;

import javax.validation.Valid;

public interface BannerService {
    Iterable<Banner> findAllBanners();
    String addBanner(@Valid BannerDto.BannerCreate bannerData);
    String updateBanner(@Valid BannerDto.BannerUpdate bannerData);
    String deleteBanner(@Valid BannerDto.BannerDelete bannerData);
}
