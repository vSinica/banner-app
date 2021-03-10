package ru.vados.JpaTestWork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vados.JpaTestWork.model.Banner;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.model.Request;
import ru.vados.JpaTestWork.repository.BannerRepository;
import ru.vados.JpaTestWork.repository.CategoryRepository;
import ru.vados.JpaTestWork.repository.RequestRepository;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class RequestController {

    CategoryRepository categoryRepository;
    BannerRepository bannerRepository;
    RequestRepository requestRepository;

    @Autowired
    public RequestController(CategoryRepository categoryRepository,
                          BannerRepository bannerRepository,
                          RequestRepository requestRepository) {
        this.categoryRepository = categoryRepository;
        this.bannerRepository = bannerRepository;
        this.requestRepository = requestRepository;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @CrossOrigin
    @Transactional
    @GetMapping("/bid")
    public ResponseEntity<Object> getAdv(HttpServletRequest request, @RequestParam("category")String reqName) throws UnknownHostException, JsonProcessingException, ParseException {

        Long category_id = categoryRepository.findCategoryIdbByReqName(reqName);
        if(category_id==null) {
            return ResponseEntity.noContent().header("Error",
                    objectMapper.writeValueAsString("No category with req name: "+ reqName)).build();
        }

        Optional<Category> category = categoryRepository.findById(category_id);

        if(!category.isPresent()){
            return ResponseEntity.noContent().header("Error", "category is null").build();
        }

        List<Banner> banners = bannerRepository.findByCategoryId(category.get());

        Request newRequest = new Request();
        newRequest.setUserAgent(request.getHeader("User-Agent"));
        newRequest.setIpAddress(Inet4Address.getLocalHost().getHostAddress());

        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        newRequest.setDatetime(timestamp);

        List<Request> requestLastdayList = requestRepository.findLastDayRequest();

        List<Banner> banersMaxCost = new ArrayList<>();
        float maxCost = 0;
        for (Banner banner : banners) {
            boolean hasRequestForThisDayAndthisIp = false;
            for (Request req : requestLastdayList) {

                if(!(req.getIpAddress().equals(newRequest.getIpAddress())) && !(req.getUserAgent().equals(newRequest.getUserAgent()))){
                    hasRequestForThisDayAndthisIp = true;
                }
                if (req.getBanner().getId().equals(banner.getId())) {
                    hasRequestForThisDayAndthisIp = true;
                }
            }

            if (!hasRequestForThisDayAndthisIp) {
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
