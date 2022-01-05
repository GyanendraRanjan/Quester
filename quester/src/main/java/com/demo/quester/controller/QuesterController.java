package com.demo.quester.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.quester.pojo.ItemDetails;
import com.demo.quester.service.QuesterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class QuesterController {

	@Autowired
	QuesterService questerService;
	
	ObjectMapper objectMapper;

	@GetMapping("/quester/{price}")
	@ResponseBody
	public String getItemList(@PathVariable int price) {
		System.out.println(price);
		List<ItemDetails> foo = questerService.someRestCall("fruits");
		List<ItemDetails> bar = questerService.someRestCall("vegetables");
		List<ItemDetails> grains = questerService.someRestCall("grains");
			 
		 List<ItemDetails> combinedList = Stream.of(foo, bar, grains)
                 .flatMap(x -> x.stream())
                 .filter(x -> x.getQty() <= price)
                 .collect(Collectors.toList());
		 
		if(combinedList.isEmpty())
			return "NO_DATA_FOUND";
		
		objectMapper=new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(combinedList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error Occured";
		}
	}
}