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
	List<ItemDetails> foo;
	List<ItemDetails> bar;
	List<ItemDetails> grains;

	@GetMapping("/quester/{price}")
	@ResponseBody
	public String getItemList(@PathVariable int price) {
		System.out.println(price);
		foo = foo == null ? questerService.someRestCall("fruits") : foo;
		bar = bar == null ? questerService.someRestCall("vegetables") : bar;
		grains = grains == null ? questerService.someRestCall("grains") : grains;

		List<ItemDetails> combinedList = Stream.of(foo, bar, grains).flatMap(x -> x.stream())
				.filter(x -> x.getQty() <= price).collect(Collectors.toList());

		if (combinedList.isEmpty())
			return "NO_FOUND";

		try {
			objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(combinedList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error Occured";
		}
	}
}