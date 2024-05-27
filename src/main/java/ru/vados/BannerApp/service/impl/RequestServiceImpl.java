package ru.vados.BannerApp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.BannerApp.entity.BannerEntity;
import ru.vados.BannerApp.entity.CategoryEntity;
import ru.vados.BannerApp.entity.RequestEntity;
import ru.vados.BannerApp.exception.NotFoundException;
import ru.vados.BannerApp.repository.BannerRepository;
import ru.vados.BannerApp.repository.CategoryRepository;
import ru.vados.BannerApp.repository.RequestRepository;
import ru.vados.BannerApp.service.RequestService;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final CategoryRepository categoryRepository;
    private final BannerRepository bannerRepository;
    private final RequestRepository requestRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public ResponseEntity<String> getAdv(ServerHttpRequest request, String reqName) throws JsonProcessingException, UnknownHostException {

        CategoryEntity category = categoryRepository.findCategoryByReqName(reqName)
                .orElseThrow(() -> new NotFoundException("No category with req name: " + reqName));

        List<BannerEntity> banners = category.getBanners();
        if(banners.isEmpty()){
            throw new NotFoundException("No banners with req name: " + reqName);
        }

        RequestEntity newRequest = new RequestEntity();
        newRequest.setUserAgent(request.getHeaders().getFirst("User-Agent"));
        newRequest.setIpAddress(Inet4Address.getLocalHost().getHostAddress());
        newRequest.setDatetime(Timestamp.from(Instant.now()));

        List<RequestEntity> requestLastDayList = requestRepository.findLastDayRequest();
        List<BannerEntity> banersMaxCost = new ArrayList<>();
        float maxCost = 0;

        for (BannerEntity banner : banners) {

            boolean hasRequestForThisDayAndThisIp = requestLastDayList.stream().noneMatch((a) -> !(a.getIpAddress().equals(newRequest.getIpAddress()))
                    && !(a.getUserAgent().equals(newRequest.getUserAgent()))
                    || a.getBanner().getId().equals(banner.getId()));

            if (hasRequestForThisDayAndThisIp) {
                if(maxCost == banner.getPrice()){
                    banersMaxCost.add(banner);
                }
                if (maxCost < banner.getPrice()) {
                    banersMaxCost.clear();
                    maxCost = banner.getPrice();
                    banersMaxCost.add(banner);
                }
            }
        }

        if(banersMaxCost.isEmpty()){
            return ResponseEntity.noContent().header("Content-Length", "0").build();
        }

        int randomBanner = new Random().nextInt(banersMaxCost.size());

        newRequest.setBanner(banersMaxCost.get(randomBanner));
        requestRepository.save(newRequest);

        banersMaxCost.get(randomBanner).addRequest(newRequest);
        bannerRepository.save(banersMaxCost.get(randomBanner));

        return ResponseEntity.ok(objectMapper.writeValueAsString(banersMaxCost.get(randomBanner).getContent()));
    }

    @Override
    public RequestEntity getRequestEntityByUserAgent(String userAgent){
        return requestRepository.findByUserAgent(userAgent);
    }
}
