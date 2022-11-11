package ru.vados.JpaTestWork.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vados.JpaTestWork.Service.RequestService;

import java.net.UnknownHostException;

@RestController
@AllArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping("/bid")
    public ResponseEntity<String> getAdv(ServerHttpRequest request,
                                         @RequestParam("reqName")String reqName) throws JsonProcessingException, UnknownHostException {
        return requestService.getAdv(request, reqName);
    }
}
