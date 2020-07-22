package com.restapi.boxoffice;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restapi.dao.*;
import com.restapi.model.*;


@RestController
public class APIController {
	
	
	@Autowired private UserDAO userDao;
	  
	@Autowired private SeatDAO seatDao;
	  
  	@Autowired private ShowDAO showDao;
  	
  	@Autowired private MovieDAO movieDao;
	 
	
	@GetMapping("/loginSuccess")
	@ResponseBody
	public String loginSucsess()
	{
		return "User Successfully Logged In!";
	}
	
	@GetMapping("/logoutSuccess")
	@ResponseBody
	public String logoutSuccess()
	{
		return "User logged out successfully!";
	}
	
	@GetMapping("/loginFailure")
	@ResponseBody
	public String loginFailed()
	{
		return "Invalid Credentials!";
	}
	
	@GetMapping("/accessDenied")
	@ResponseBody
	public String accessDenied()
	{
		return "Operation not permited!";
	}
	
	@GetMapping("/reserve")
	@ResponseBody
	public boolean reserve(@RequestParam(name = "screen") int screenNo, @RequestParam(name = "row") int rowNo, @RequestParam(name = "seat") int seatNo, @RequestParam(name = "show") int showId)
	{
		Seat seat = new Seat();
		seat.setScreenNo(screenNo);
		seat.setRowNo(rowNo);
		seat.setSeatNo(seatNo);
		
		return AdminControl.reserve(seat, showDao.getShowById(showId));
	}
	
	@GetMapping("/addSeat")
	@ResponseBody
	public boolean addSeat(@RequestParam(name = "screen") int screenNo, @RequestParam(name = "row") int rowNo, @RequestParam(name = "price") double price, @RequestParam(name = "category") String category, @RequestParam(name = "space-after") boolean spaceAfter)
	{
		return seatDao.addSeat(screenNo, rowNo, price, category, spaceAfter);
	}
	
	@GetMapping("/getSeats")
	@ResponseBody
	public List<Seat> getSeats(@RequestParam(name = "screen") int screenNo)
	{
		return seatDao.getSeats(screenNo);
	}
	
	@GetMapping("/changePrice")
	@ResponseBody
	public boolean changePrice(@RequestParam(name = "screen") int screenNo, @RequestParam(name = "row") int rowNo, @RequestParam(name = "seat") int seatNo, @RequestParam(name = "price") double price)
	{
		return seatDao.changePrice(screenNo, rowNo, seatNo, price);
	}
	
	@GetMapping("/addShow")
	@ResponseBody
	public boolean addShow(@RequestParam(name = "screen") int screenNo, @RequestParam(name = "show-date") String date, @RequestParam(name = "show-time") String time, @RequestParam(name = "movie") int movieId)
	{
		return showDao.addShow(screenNo, date, time, movieId);
	}
	
	@GetMapping("/addMovie")
	@ResponseBody
	public boolean addMovie(@RequestParam(name = "title") String title, @RequestParam(name = "runtime") int runtime, @RequestParam(name = "release-date") String releaseDt)
	{
		Movie movie = new Movie();
		
		movie.setTitle(title);
		movie.setRunTime(runtime);
		movie.setReleaseDt(releaseDt);
		
		return movieDao.addMovie(movie);
	}
	
	@GetMapping("/getMovies")
	@ResponseBody
	public List<Movie> getMovies()
	{
		return movieDao.getMovies();
	}
	
	@GetMapping("/addUser")
	@ResponseBody
	public boolean addUser(@RequestParam(name = "username") String userName, @RequestParam(name = "password") String password, @RequestParam(name = "admin") boolean isAdmin)
	{
		return userDao.addUser(userName, password, isAdmin);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
