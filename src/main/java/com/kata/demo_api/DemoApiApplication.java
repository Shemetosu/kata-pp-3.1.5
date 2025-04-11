package com.kata.demo_api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApiApplication {

    public static final String BASE_URL = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);
        String session = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        System.out.println("Полученный session cookie: " + session);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", session);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String post = "{ \"id\": 3, \"name\": \"James\", \"lastName\": \"Brown\", \"age\": 30 }";
        HttpEntity<String> postRequest = new HttpEntity<>(post, headers);
        ResponseEntity<String> postResponse = restTemplate.postForEntity(BASE_URL, postRequest, String.class);
        String part1 = postResponse.getBody();
        System.out.println("Ответ на POST запрос: " + part1);

        String put = "{ \"id\": 3, \"name\": \"Thomas\", \"lastName\": \"Shelby\", \"age\": 30 }";
        HttpEntity<String> putRequest = new HttpEntity<>(put, headers);
        ResponseEntity<String> putResponse = restTemplate.exchange(BASE_URL, HttpMethod.PUT, putRequest, String.class);
        String part2 = putResponse.getBody();
        System.out.println("Ответ на PUT запрос: " + part2);

        HttpEntity<Void> deleteRequest = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(BASE_URL + "/3", HttpMethod.DELETE, deleteRequest, String.class);
        String part3 = deleteResponse.getBody();
        System.out.println("Ответ на DELETE запрос: " + part3);

        String result = part1 + part2 + part3;
        System.out.println("Итоговый результат: " + result);
    }
}
