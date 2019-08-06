package com.cj.httpClient.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * Test @HystrixCommand --未通过
 * 该注解最好不要应用在Controller上.
 */
@RestController
@RequestMapping("/hystrixCommandController")
@Slf4j
public class HystrixCommandController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/get")
   // @HystrixCommand(fallbackMethod = "testFallback")
    public Object restTemplate_get() {
        int param = 1;
        String url = String.format("http://localhost:8081/hystrixCommandController/getApi/%s",param);
        return restTemplate.getForEntity(url, A.class).getBody();
    }

    @GetMapping("/getApi/{id}")
    public Object restTemplate_called_genJson(@PathVariable("id") int id) {
        return new A().setId(id).setName("testName");
    }

    void testFallback() {
        System.out.println("@@@testFallback...");
    }

    static class A {
      private int id;
      private String name;

        public int getId() {
            return id;
        }

        public A setId(int id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public A setName(String name) {
            this.name = name;
            return this;
        }
    }


}
