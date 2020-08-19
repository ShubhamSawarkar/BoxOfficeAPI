package com.restapi.boxoffice;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restapi.model.QueryResult;
import com.restapi.service.*;


@RestController
public class APIController {
	
	@Autowired
	private SeatService seatService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShowService showService;
	
	@GetMapping("/accessDenied")
	@ResponseBody
	public String accessDenied()
	{
		return "Operation not permited!";
	}
	
	@PostMapping("/reserve")
	@ResponseBody
	public QueryResult reserve(@RequestParam(name = "screen") int screenNo, @RequestParam(name = "row") int rowNo, @RequestParam(name = "seat") int seatNo, @RequestParam(name = "show") int showId)
	{
		return seatService.reserve(screenNo, rowNo, seatNo, showId);
	}
	
	@PostMapping("/addSeat")
	@ResponseBody
	public QueryResult addSeat(@RequestParam(name = "screen") int screenNo, @RequestParam(name = "row") int rowNo, @RequestParam(name = "price") double price, @RequestParam(name = "category") String category, @RequestParam(name = "space-after") boolean spaceAfter)
	{
		return seatService.addSeat(screenNo, rowNo, price, category, spaceAfter);
	}
	
	@PostMapping("/getSeats")
	@ResponseBody
	public List<QueryResult> getSeats(@RequestParam(name = "screen") int screenNo)
	{
		return seatService.getSeats(screenNo);
	}
	
	@PostMapping("/changePrice")
	@ResponseBody
	public QueryResult changePrice(@RequestParam(name = "screen") int screenNo, @RequestParam(name = "row") int rowNo, @RequestParam(name = "seat") int seatNo, @RequestParam(name = "price") double price)
	{
		return seatService.changePrice(screenNo, rowNo, seatNo, price);
	}
	
	@PostMapping("/getShows")
	@ResponseBody
	public List<QueryResult> getShows()
	{
		return showService.getShows();
	}
	
	@PostMapping("/getMovies")
	@ResponseBody
	public List<QueryResult> getMovies()
	{
		return movieService.getMovies();
	}
	
	@PostMapping("/getStatus")
	@ResponseBody
	public List<Boolean> getStatus(@RequestParam(name = "show-id") int showId)
	{
		return showService.getShowStatus(showId);
	}
	
}
