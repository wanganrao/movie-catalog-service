package com.example.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.moviecatalogservice.models.MovieItem;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MovieInfoService {

	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackMovieInfo")
	public MovieItem getMovieInfo(String movieId) {
		return restTemplate.getForObject("http://movie-info-service/movies/"+movieId, MovieItem.class);
		
	}
	
	public MovieItem getFallbackMovieInfo(String movieId) {
		return new MovieItem("No movie found","");		
	}
}
