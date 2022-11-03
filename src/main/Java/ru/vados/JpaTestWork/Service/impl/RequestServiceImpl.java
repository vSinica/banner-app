package ru.vados.JpaTestWork.Service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.JpaTestWork.Entity.Banner;
import ru.vados.JpaTestWork.Entity.Category;
import ru.vados.JpaTestWork.Entity.Request;
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
    public ResponseEntity<Object> getAdv(ServerHttpRequest request, String reqName) throws JsonProcessingException, UnknownHostException {

        Category category;
        Optional<Category> optCategory = categoryRepository.findCategoryByReqName(reqName);
        if(optCategory.isPresent()){
            category = optCategory.get();
        } else {
            return ResponseEntity.noContent().header("Error",
                    objectMapper.writeValueAsString("No category with req name: "+ reqName)).build();
        }

        List<Banner> banners = category.getBanners();
        if(banners.isEmpty()){
            return ResponseEntity.noContent().header("Error",
                    objectMapper.writeValueAsString("No banners with req name: "+ reqName)).build();
        }

        Request newRequest = new Request();
        newRequest.setUserAgent(request.getHeaders().getFirst("User-Agent"));
        newRequest.setIpAddress(Inet4Address.getLocalHost().getHostAddress());
        newRequest.setDatetime(Timestamp.from(Instant.now()));

        List<Request> requestLastDayList = requestRepository.findLastDayRequest();
        List<Banner> banersMaxCost = new ArrayList<>();
        float maxCost = 0;

        for (Banner banner : banners) {
            boolean hasRequestForThisDayAndThisIp = false;
            for (Request currentRequest : requestLastDayList) {

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
