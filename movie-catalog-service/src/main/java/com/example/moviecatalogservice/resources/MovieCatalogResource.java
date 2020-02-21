package com.example.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.moviecatalogservice.models.Catalog;
import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.MovieItem;
import com.example.moviecatalogservice.models.RatingItem;
import com.example.moviecatalogservice.services.MovieInfoService;
import com.example.moviecatalogservice.services.UserRatingsService;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	MovieInfoService movieInfoService;

	@Autowired
	UserRatingsService userRatingsService;
	
	@RequestMapping("/{userId}")
	public Catalog getCatalog(@PathVariable("userId") String userId) {
		RatingItem[] ratings = userRatingsService.getUserRatingsInfo(userId);
		
		Catalog catalog = new Catalog();
		for(RatingItem r:ratings) {
			MovieItem m=movieInfoService.getMovieInfo(r.getMovieId());
			catalog.getCatalogItems().add(new CatalogItem(m.getName(), m.getDesc(), r.getRating()));
		}
		return catalog;	
		
	}
	

}
