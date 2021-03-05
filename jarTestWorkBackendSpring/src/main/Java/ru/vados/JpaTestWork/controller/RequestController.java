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
    public ResponseEntity<Object> getAdv(HttpServletRequest request, @RequestParam("category")String req_name) throws UnknownHostException, JsonProcessingException, ParseException {
        System.out.println("user-agent: "+ request.getHeader("User-Agent"));
        System.out.println("ip ------  " + Inet4Address.getLocalHost().getHostAddress());
        System.out.println("Req category from request - "+req_name);

        Long category_id = categoryRepository.findCategoryIdByReqName(req_name);
        if(category_id==null) {
            return ResponseEntity.noContent().header("Error",
                    objectMapper.writeValueAsString("No category with req name: "+ req_name)).build();
        }

        Optional<Category> category = categoryRepository.findById(category_id);

        if(!category.isPresent()){
            return ResponseEntity.noContent().header("Error", "category is null").build();
        }

        List<Banner> baners = bannerRepository.findBannersByCategoryId(category.get().getId());

        Request newRequest = new Request();
        newRequest.setUserAgent(request.getHeader("User-Agent"));
        newRequest.setIpAddress(Inet4Address.getLocalHost().getHostAddress());

        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        newRequest.setDatetime(timestamp);

        List<Request> requestLastdayList = requestRepository.findLastDayRequest();
        System.out.println(" last day request:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        for (Request req: requestLastdayList) {
            System.out.println(req.getDatetime()+"  -datetime | baner id -  " + req.getBanner().getId());
        }

        List<Banner> banersMaxCost = new ArrayList<>();
        float maxCost = 0;
        for (Banner baner : baners) {
            boolean hasRequestForThisDayAndthisIp = false;
            for (Request req : requestLastdayList) {
                if(req.getIpAddress()==newRequest.getIpAddress() && req.getUserAgent()==newRequest.getUserAgent()){
                    hasRequestForThisDayAndthisIp = true;
                }
                if (req.getBanner().getId()==(baner.getId())) {
                    hasRequestForThisDayAndthisIp = true;
                }
            }

            if (!hasRequestForThisDayAndthisIp) {
                if(maxCost == baner.getPrice()){
                    banersMaxCost.add(baner);
                }
                if (maxCost < baner.getPrice()) {
                    banersMaxCost.clear();
                    maxCost = baner.getPrice();
                    banersMaxCost.add(baner);
                }
            }
        }

        if(banersMaxCost.isEmpty()){
            return ResponseEntity.noContent().header("Content-Length", "0").build();
        }

        System.out.println("banner.size()  "+   banersMaxCost.size());
        int randomBaner = new Random().nextInt(banersMaxCost.size());
        System.out.println("randomBaner =  " + randomBaner);

        newRequest.setBanner(banersMaxCost.get(randomBaner));
        requestRepository.save(newRequest);

        banersMaxCost.get(randomBaner).addRequest(newRequest);
        bannerRepository.save(banersMaxCost.get(randomBaner));

        return ResponseEntity.ok(objectMapper.writeValueAsString(banersMaxCost.get(randomBaner).getContent()));
    }
}
