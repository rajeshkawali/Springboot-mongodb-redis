package com.rajeshkawali.service;

import com.rajeshkawali.dto.University;
import com.rajeshkawali.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Rajesh_Kawali
 */
@Slf4j
@Service
public class ExternalServiceImpl implements ExternalService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${api.base.universities.url}")
    private String universitiesUrl;

    public List<University> getUniversitiesByCountry(String country) {
        log.info("getUniversitiesByCountry::ENTER");
        try {
            List<University> universitiesFromRedis = (List<University>) redisUtil.getFromCache(country, Object.class);
            if (universitiesFromRedis != null) {
                log.info("getUniversitiesByCountry::Universities From Redis");
                return universitiesFromRedis;
            } else {
                WebClient webClient = webClientBuilder.baseUrl(universitiesUrl).build();
                List<University> universities = webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/search").queryParam("country", country).build())
                        .retrieve()
                        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                                clientResponse -> Mono.error(new RuntimeException("API call failed!")))
                        .bodyToFlux(University.class)
                        .collectList()
                        .block();
                if (universities != null) {
                    redisUtil.setIntoCache(country, universities, 240l); // 240 -> 4 min
                }
                log.info("getUniversitiesByCountry::Universities From API");
                return universities;
            }
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw e;
        }
    }

}
