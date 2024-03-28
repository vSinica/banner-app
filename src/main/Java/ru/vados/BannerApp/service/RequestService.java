package ru.vados.BannerApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import ru.vados.BannerApp.entity.RequestEntity;

import java.net.UnknownHostException;

public interface RequestService {
    ResponseEntity<String> getAdv(ServerHttpRequest request, String reqName) throws JsonProcessingException, UnknownHostException;

    RequestEntity getRequestEntityByUserAgent(String userAgent);
}
