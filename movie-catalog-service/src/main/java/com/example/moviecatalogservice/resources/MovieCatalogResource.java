package com.example.moviecatalogservice.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.moviecatalogservice.models.Catalog;
import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.MovieItem;
import com.example.moviecatalogservice.models.RatingItem;

@RestController
@RequestMapping("/catalog")
@EnableEurekaClient
public class MovieCatalogResource {

	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping("/{userId}")
	public Catalog getCatalog(@PathVariable("userId") String userId) {
		RatingItem[] ratings = restTemplate.getForObject("http://ratings-data-service/ratings/" + userId, RatingItem[].class);
//		RatingItem[] ratings = restTemplate.getForObject("http://localhost:8083/ratings/shashi", RatingItem[].class);
		Catalog catalog = new Catalog();
		for(RatingItem r:ratings) {
			MovieItem m=restTemplate.getForObject("http://movie-info-service/movies/"+r.getMovieId(), MovieItem.class);
//			MovieItem m=restTemplate.getForObject("http://localhost:8082/movies/"+r.getMovieId(), MovieItem.class);
			catalog.getCatalogItems().add(new CatalogItem(m.getName(), m.getDesc(), r.getRating()));
		}
		return catalog;
		
	}
}
