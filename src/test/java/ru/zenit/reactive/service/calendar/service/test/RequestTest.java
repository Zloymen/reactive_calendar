package ru.zenit.reactive.service.calendar.service.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import ru.zenit.reactive.service.calendar.dto.AnswerDto;

import java.util.List;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Slf4j
public class RequestTest {

    private WebClient client = WebClient.create();

    private final String url = "https://data.gov.ru/api/json/dataset/7708660670-proizvcalendar/version/20151123T183036/content/";

    private final String token = "<add you token>";

    private final int year = 2018;

    @Test
    public void getGovData() throws InterruptedException {

        UriComponentsBuilder builder = fromHttpUrl(url).queryParam("access_token", token).queryParam("search", year);

        client
                .get()
                .uri(uriBuilder -> builder.build().toUri())
                .exchange()
                .flatMap(item -> item.bodyToMono(new ParameterizedTypeReference<List<AnswerDto>>() {
                }))
                .flatMap(item -> item.isEmpty() ? Mono.empty() : Mono.just(item.get(0)))
                .map(item -> {
                    log.info("run !!!!");
                    log.info(item.toString());
                    return item;
                }).subscribe(answerDto -> log.info(answerDto.toString()));

    }
}
