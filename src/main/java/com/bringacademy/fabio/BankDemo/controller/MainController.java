package com.bringacademy.fabio.BankDemo.controller;

import com.bringacademy.fabio.BankDemo.model.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/banks")
public class MainController {

    private final String APIURL = "https://api.openbankproject.com/obp/v4.0.0/banks";

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @GetMapping("/")
    public List<Bank> getBanks(@RequestParam(value="bank", defaultValue = "all") String bank) {
//        URI uri = new URI(APIURL);
        Bank.BankGroup bg = restTemplate.getForObject(APIURL, Bank.BankGroup.class);
        List<Bank> banks = bg.getBanks();
        if (bank.equals("all")){
            return banks;
        }
        return banks.stream()
                .filter(b -> b.getShortName() != null && b.getShortName().toLowerCase(Locale.ROOT).contains(bank) ||
                        b.getFullName() != null && b.getFullName().toLowerCase(Locale.ROOT).contains(bank))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Bank getBank(@PathVariable(name = "id") String id){
        /**
         * First way, get the full list and return the one with the same ID
         */
        Bank.BankGroup bg = restTemplate.getForObject(APIURL, Bank.BankGroup.class);
        if (bg == null) return null;
        if (bg.getBanks().isEmpty()) return null;

        List<Bank> banks = bg.getBanks();
        return banks.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow();
                                                    // with a error message
        /**
         * Second way, use the core own way of getting a bank by ID
         *

        // Oh no, catching an unchecked exception...
        try {
            return restTemplate.getForObject(APIURL + "/" + id, Bank.class);
        } catch (HttpClientErrorException e) {
            return new Bank();                          // and returning an empty object
        }

        // Possible solution, return a string, be it the stringyfied objects, or the errors.
        */
    }
}
