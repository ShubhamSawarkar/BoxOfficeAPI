package com.restapi.boxoffice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.restapi.model.QueryError;
import com.restapi.model.QueryResult;
import com.restapi.model.Seat;
import com.restapi.model.Show;
import com.restapi.service.*;

@Controller
public class UIController 
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private SeatService seatService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private ShowService showService;
	
	@GetMapping({"/AdminLogin", "/", "/login"})
	public String homePage()
	{
		return "jsp/AdminLogin";
	}
	
	@GetMapping("/loginSuccess")
	public String loginSucsess()
	{
		if(userService.isAdmin())
		{
			return "jsp/ControlPanel";
		}
		else
		{
			return "jsp/UserLogin";
		}
	}
	
	@GetMapping("/loginFailure")
	public String loginFailed(@RequestParam(value = "error", required = false) boolean error)
	{
		return "jsp/AdminLogin";
	}
	
	@GetMapping("/logoutSuccess")
	public String logoutSuccess()
	{
		return "jsp/AdminLogin";
	}
	
	@GetMapping("/AddShow")
	public ModelAndView addShow()
	{
		ModelAndView model = new ModelAndView("jsp/AddShow");
		model.addObject("screens", seatService.getScreens());
		model.addObject("movies", movieService.getMovieObjects());
		return model;
	}
	
	@GetMapping("/AddMovie")
	public ModelAndView addMovie()
	{
		ModelAndView model = new ModelAndView("jsp/AddMovie");
		return model;
	}
	
	@GetMapping("/AddUser")
	public ModelAndView addUser()
	{
		ModelAndView model = new ModelAndView("jsp/AddUser");
		return model;
	}
	
	@PostMapping("/addShow")
	public ModelAndView addShow(@RequestParam(name = "screen") int screenNo, @RequestParam(name = "show-date") String date, @RequestParam(name = "show-time") String time, @RequestParam(name = "movie") int movieId)
	{
		ModelAndView model = new ModelAndView("jsp/Confirmation");
		model.addObject("embed", "/AddShow.jsp");
		if(showService.addShow(screenNo, date, time, movieId) != null)
		{
			model.addObject("status", "success");
			return model;
		}
		else
		{
			System.out.println("error 2");
			model.addObject("status", "failure");
			return model;
		}
	}
	
	@PostMapping("/addMovie")
	public ModelAndView addMovie(@RequestParam(name = "title") String title, @RequestParam(name = "runtime") int runtime, @RequestParam(name = "release-date") String releaseDt)
	{
		ModelAndView model = new ModelAndView("jsp/Confirmation");
		
		if(movieService.addMovie(title, runtime, releaseDt) != null)
		{
			model.addObject("status", "success");
			return model;
		}
		else
		{
			model.addObject("status", "failure");
			return model;
		}
	}
	
	@PostMapping("/addUser")
	public ModelAndView addUser(@RequestParam(name = "username") String userName, @RequestParam(name = "password") String password, @RequestParam(name = "admin") boolean isAdmin)
	{
		ModelAndView model = new ModelAndView("jsp/Confirmation");
		if(userService.addUser(userName, password, isAdmin) != null)
		{
			model.addObject("status", "success");
			return model;
		}
		else
		{
			model.addObject("status", "failure");
			return model;
		}
		//return userService.addUser(userName, password, isAdmin);
	}
	
	@PostMapping("/SeatsViewChange")
	public ModelAndView seatsView(@RequestParam(name = "show") int showId)
	{
		ModelAndView model = new ModelAndView("jsp/SeatsView");
		model.addObject("shows", showService.getShows());
		model.addObject("seats", seatService.getSeats(showService.getShowById(showId).getScreenNo()));
		model.addObject("status", showService.getShowStatus(showId));
		 return model;
	}
	
	@GetMapping("/SeatsView")
	public ModelAndView seatsView()
	{
		ModelAndView model = new ModelAndView("jsp/SeatsView");
		model.addObject("shows", showService.getShows());
		model.addObject("seats", seatService.getSeats(((Show)showService.getShows().get(0)).getScreenNo()));
		model.addObject("status", showService.getShowStatus(((Show)showService.getShows().get(0)).getID()));
		return model;
	}
	
	@GetMapping("/DisableSeat")
	public ModelAndView disableSeat()
	{
		ModelAndView model = new ModelAndView("jsp/DisableSeat");
		model.addObject("shows", showService.getShows());
		model.addObject("seats", seatService.getSeats(((Show)showService.getShows().get(0)).getScreenNo()));
		model.addObject("status", showService.getShowStatus(((Show)showService.getShows().get(0)).getID()));
		model.addObject("show", (Show)showService.getShows().get(0));
		return model;
	}
	
	@PostMapping("/DisableSeatChange")
	public ModelAndView disableSeat(@RequestParam(name = "show") int showId)
	{
		ModelAndView model = new ModelAndView("jsp/SeatsView");
		model.addObject("shows", showService.getShows());
		model.addObject("seats", seatService.getSeats(showService.getShowById(showId).getScreenNo()));
		model.addObject("status", showService.getShowStatus(showId));
		model.addObject("show", showService.getShowById(showId));
		return model;
	}
	
	@PostMapping("/reserveSeats")
	public ModelAndView reserveSeats(@RequestParam(name = "checked") String[] selectedSeats, @RequestParam(name = "show") int showId)
	{
		
		  List<Seat> seats = new ArrayList<Seat>(); 
		  for(QueryResult seat :seatService.getSeats(showService.getShowById(showId).getScreenNo())) 
		  	{
			  for(String seatId : selectedSeats)
			  {
				  if(((Seat) seat).getSeatID() == Integer.parseInt(seatId))
				  {
					  seats.add((Seat) seat);
				  }
			  }
		  	} 
		  
		  for(Seat seat : seats)
		  {
			  seatService.reserve(seat.getScreenNo(), seat.getRowNo(), seat.getSeatNo(), showId);
		  }
		   
		  ModelAndView model = new ModelAndView("jsp/Confirmation"); 
		  model.addObject("seats", seats); model.addObject("status", "success"); 
		  return model;
		 
		
	}
	
	@GetMapping("/ShowSelect")
	public ModelAndView reserveResult()
	{
		ModelAndView model = new ModelAndView("jsp/ReserveResult");
		model.addObject("shows", showService.getShows());
		return model;
	}
	
	@PostMapping("/book")
	public ModelAndView book(@RequestParam(name = "row") int rowNo, @RequestParam(name = "seat") int seatNo, @RequestParam(name = "show") int showId)
	{
		ModelAndView model = new ModelAndView("jsp/Confirmation");
		if(!(seatService.reserve(showService.getShowById(showId).getScreenNo(), rowNo, seatNo, showId) instanceof QueryError))
		{
			model.addObject("status", "success");
			return model;
		}
		else
		{
			model.addObject("status", "failure");
			return model;
		}
	}
	
	@PostMapping("/SeatSelect")
	public ModelAndView seatSelect(@RequestParam(name = "show") int showId)
	{
		Show show = showService.getShowById(showId);
		ModelAndView model = new ModelAndView("jsp/SelectSeat");
		int maxRow=0, maxSeat=0;
		for(QueryResult seat : seatService.getSeats(show.getScreenNo()))
		{
			if(((Seat)seat).getRowNo() > maxRow)
			{
				maxRow = ((Seat)seat).getRowNo();
			}
			
			if(((Seat) seat).getSeatNo() > maxSeat)
			{
				maxSeat = ((Seat) seat).getSeatNo();
			}
		}
		model.addObject("maxRow", maxRow);
		model.addObject("maxSeat", maxSeat);
		model.addObject("show", showId);
		return model;
	}
}
