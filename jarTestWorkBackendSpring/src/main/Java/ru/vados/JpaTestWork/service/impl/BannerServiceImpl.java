package ru.vados.JpaTestWork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vados.JpaTestWork.model.Banner;
import ru.vados.JpaTestWork.repository.BannerRepository;
import ru.vados.JpaTestWork.service.BannerService;

import java.util.Optional;

@Service
public class BannerServiceImpl implements BannerService {
    BannerRepository bannerRepository;

    @Autowired
    public BannerServiceImpl(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public void saveBanner(Banner banner) {
        bannerRepository.save(banner);
    }

    public Optional<Banner> findBannerById(Long bannerId){
        return bannerRepository.findById(bannerId);
    }

    public Iterable<Banner> findAllBanners(){
        return bannerRepository.findAll();
    }
}
