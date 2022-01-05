package com.demo.quester.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.demo.quester.pojo.ItemDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Service
public class QuesterService {

    private final WebClient webClient;

    public QuesterService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://f8776af4-e760-4c93-97b8-70015f0e00b3.mock.pstmn.io").
        		exchangeStrategies(ExchangeStrategies.builder().codecs(this::acceptedCodecs).build()).
        		build();
    }
    
    private void acceptedCodecs(ClientCodecConfigurer clientCodecConfigurer) {
        clientCodecConfigurer.customCodecs().registerWithDefaultConfig(new Jackson2JsonDecoder(new ObjectMapper(), MediaType.TEXT_HTML));
        clientCodecConfigurer.customCodecs().registerWithDefaultConfig(new Jackson2JsonEncoder(new ObjectMapper(), MediaType.TEXT_HTML));
      }
    
    public List<ItemDetails> someRestCall(String name) {
		Mono<List<ItemDetails>> response = webClient.get().uri("/{name}", name)
    			  .accept(MediaType.APPLICATION_JSON)
    			  .retrieve()
    			  .bodyToMono(new ParameterizedTypeReference<List<ItemDetails>>() {});
    			List<ItemDetails> itemDetails = response.block();
    			
    			System.out.println(itemDetails);

    			return itemDetails;
    }

}