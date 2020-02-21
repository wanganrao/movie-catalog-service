package com.example.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.moviecatalogservice.models.RatingItem;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserRatingsService {
	@Autowired
	RestTemplate restTemplate;
	

	@HystrixCommand(fallbackMethod = "getFallbackUserRatingsInfo")
	public RatingItem[] getUserRatingsInfo(String userId) {
		return restTemplate.getForObject("http://ratings-data-service/ratings/" + userId, RatingItem[].class);

		
	}
	
	public RatingItem[] getFallbackUserRatingsInfo(String movieId) {
		RatingItem[] ratings = new RatingItem[1];
		ratings[0] = new RatingItem("0",0);
		return ratings;		
	}
}
