package ru.vados.JpaTestWork.Service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.JpaTestWork.Entity.BannerEntity;
import ru.vados.JpaTestWork.Entity.CategoryEntity;
import ru.vados.JpaTestWork.Entity.RequestEntity;
import ru.vados.JpaTestWork.Exception.NotFoundException;
import ru.vados.JpaTestWork.Repository.BannerRepository;
import ru.vados.JpaTestWork.Repository.CategoryRepository;
import ru.vados.JpaTestWork.Repository.RequestRepository;
import ru.vados.JpaTestWork.Service.RequestService;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        CategoryEntity category;
        Optional<CategoryEntity> optCategory = categoryRepository.findCategoryByReqName(reqName);
        if(optCategory.isPresent()){
            category = optCategory.get();
        } else {
            throw new NotFoundException("No category with req name: " + reqName);
        }

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
            boolean hasRequestForThisDayAndThisIp = false;
            for (RequestEntity currentRequest : requestLastDayList) {

                if(!(currentRequest.getIpAddress().equals(newRequest.getIpAddress())) && !(currentRequest.getUserAgent().equals(newRequest.getUserAgent()))){
                    hasRequestForThisDayAndThisIp = true;
                }
                if (currentRequest.getBanner().getId().equals(banner.getId())) {
                    hasRequestForThisDayAndThisIp = true;
                }
            }

            if (!hasRequestForThisDayAndThisIp) {
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

}
