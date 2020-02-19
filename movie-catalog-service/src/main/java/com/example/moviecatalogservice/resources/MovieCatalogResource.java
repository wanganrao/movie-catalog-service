package com.example.moviecatalogservice.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.MovieItem;
import com.example.moviecatalogservice.models.RatingItem;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	//@Autowired consumes the bean, here variable type matters and not the name
	@Autowired
	RestTemplate rt;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		
		//Get ratings for the userId
		RatingItem[] ratings = rt.getForObject("http://localhost:8083/ratings/" + userId, RatingItem[].class);
		
		//Now get movie info for each movie in the ratings
		List<CatalogItem> catalog = new ArrayList<CatalogItem>();
		for(RatingItem r:ratings) {
			MovieItem m = rt.getForObject("http://localhost:8082/movies/" + r.getMovieId(), MovieItem.class);
			catalog.add(new CatalogItem(m.getName(), m.getDesc(), r.getRating()));
		}
		return catalog;
	}

}
