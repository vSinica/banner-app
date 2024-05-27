package ru.vados.BannerAppTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import ru.vados.BannerApp.entity.RequestEntity;
import ru.vados.BannerApp.service.RequestService;

import java.net.URI;
import java.net.UnknownHostException;

@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
                "/sql/ddl-before-test.sql",
                "/sql/prepare-test-set1.sql"
        })
})
public class RequestServiceTest extends AbstractTest {


    @Autowired
    private RequestService requestService;


    @Test
    public void test__get_advertise() throws UnknownHostException, JsonProcessingException {
        ServerHttpRequest request = new ServerHttpRequest() {
            @Override
            public String getId() {
                return null;
            }

            @Override
            public RequestPath getPath() {
                return null;
            }

            @Override
            public MultiValueMap<String, String> getQueryParams() {
                return null;
            }

            @Override
            public MultiValueMap<String, HttpCookie> getCookies() {
                return null;
            }

            @Override
            public String getMethodValue() {
                return null;
            }

            @Override
            public URI getURI() {
                return null;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return null;
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("User-Agent", "test");
                return httpHeaders;
            }
        };
        requestService.getAdv(request,"category1reqId");

        RequestEntity requestEntity = requestService.getRequestEntityByUserAgent("test");
        Assertions.assertEquals(requestEntity.getUserAgent(),"test");
        Assertions.assertEquals(requestEntity.getBanner().getCategory().getReqName(),"category1reqId");

        requestService.getAdv(request, "category1reqId");
        Assertions.assertNull(requestService.getAdv(request, "category1reqId").getBody());

    }

}
