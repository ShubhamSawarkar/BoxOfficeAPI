package com.restapi.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AdminControl {

	private static final String DB_NAME = "boxoffice.sqlite";
	private static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;
	
	private static final String TABLE_SEATS = "Seats";
	private static final String TABLE_RESERVATION = "Reservation";
	private static final String TABLE_SHOWS = "Shows";
	private static final String TABLE_MOVIES = "Movies";
	
	private static final String COLUMN_SEATS_ID = "Seat_ID";
	private static final String COLUMN_SEATS_SCREENNO = "Screen_No";
	private static final String COLUMN_SEATS_ROWNO = "Row_No";
	private static final String COLUMN_SEATS_SEATNO = "Seat_No";
	private static final String COLUMN_SEATS_CATEGORY = "Category";
	private static final String COLUMN_SEATS_PRICE = "Price";
	private static final String COLUMN_SEATS_SPACE = "Space_After";
	private static final String COLUMN_RESERVATION_SEATID = "Seat_ID";
	private static final String COLUMN_RESERVATION_SHOWID = "Show_ID";
	private static final String COLUMN_SHOWS_ID = "Show_ID";
	private static final String COLUMN_SHOWS_TIME = "Time";
	private static final String COLUMN_SHOWS_DATE = "Date";
	private static final String COLUMN_SHOWS_SCREENNO = "Screen_No";
	private static final String COLUMN_SHOWS_MOVIEID = "Movie_ID";
	private static final String COLUMN_MOVIES_ID = "Movie_ID";
	private static final String COLUMN_MOVIES_TITLE = "Title";
	private static final String COLUMN_MOVIES_RUNTIME = "Runtime";
	private static final String COLUMN_MOVIES_RELEASEDT = "Release_Date";
	private static final String COLUMN_MOVIES_LASTSHOWDT = "Last_Show_Date";
	
	private static final int INDEX_SEATS_ID = 1;
	private static final int INDEX_SEATS_SCREENNO = 2;
	private static final int INDEX_SEATS_ROWNO = 3;
	private static final int INDEX_SEATS_SEATNO = 4;
	private static final int INDEX_MOVIES_RELEASEDT = 4;
	
	private static Connection connection;
	
	private static volatile boolean isUpdating = false;
	
	private static final String QUERY_ADDSEAT = "INSERT INTO " + TABLE_SEATS +"(" + COLUMN_SEATS_ID + ", " + COLUMN_SEATS_SCREENNO + ", " + COLUMN_SEATS_ROWNO + ", " + COLUMN_SEATS_SEATNO + ", "
												+ COLUMN_SEATS_CATEGORY + ", " + COLUMN_SEATS_PRICE + ", " + COLUMN_SEATS_SPACE + ") VALUES(?, ? ,?, ?, ?, ?, ?)";
	
	private static final String QUERY_ADDSHOW = "INSERT INTO " + TABLE_SHOWS + "(" + COLUMN_SHOWS_ID + ", " + COLUMN_SHOWS_DATE + ", " + COLUMN_SHOWS_TIME + ", " + COLUMN_SHOWS_SCREENNO + ", " + 
												COLUMN_SHOWS_MOVIEID + ") VALUES(?, ?, ?, ?, ?)";
	
	private static final String QUERY_CHANGE_PRICE = "UPDATE " + TABLE_SEATS + " SET " + COLUMN_SEATS_PRICE + " = ? WHERE " + COLUMN_SEATS_SCREENNO + " = ? AND " + COLUMN_SEATS_ROWNO + " = ? AND "
												+ COLUMN_SEATS_SEATNO + " = ?";
	
	private static final String QUERY_LAST_SCREEN = "SELECT IFNULL(MAX(" + COLUMN_SEATS_SCREENNO + "), 0) FROM "+ TABLE_SEATS;
	private static final String QUERY_LAST_ROW = "SELECT MAX(" + COLUMN_SEATS_ROWNO + ") FROM " + TABLE_SEATS + " WHERE " + COLUMN_SEATS_SCREENNO + " = ?"; 
	private static final String QUERY_NEXT_SEATID = "SELECT IFNULL(MAX(" + COLUMN_SEATS_ID + "), 0) FROM " + TABLE_SEATS;
	private static final String QUERY_LAST_SEAT = "SELECT IFNULL(MAX(" + COLUMN_SEATS_SEATNO + "), 0) FROM " + TABLE_SEATS + " WHERE " + COLUMN_SEATS_SCREENNO +" = ? AND " + COLUMN_SEATS_ROWNO + " = ?";
	private static final String QUERY_NEXT_SHOWID = "SELECT IFNULL(MAX(" + COLUMN_SHOWS_ID + "), 0) FROM " + TABLE_SHOWS;
	private static final String QUERY_MOVIE_EXISTS = "SELECT COUNT(*), " + COLUMN_MOVIES_RELEASEDT + " FROM " + TABLE_MOVIES + " WHERE " + COLUMN_MOVIES_ID + " = ?";
	private static final String QUERY_SCREEN_EXISTS = "SELECT COUNT(*) FROM " + TABLE_SEATS + " WHERE " + COLUMN_SEATS_SCREENNO + " = ?"; 
	private static final String QUERY_SHOW_EXISTS = "SELECT COUNT(*) FROM " + TABLE_SHOWS + " WHERE " + COLUMN_SHOWS_SCREENNO + " = ? AND " + COLUMN_SHOWS_MOVIEID + " = ? AND " + COLUMN_SHOWS_DATE + " = ?"
													+ " AND " + COLUMN_SHOWS_TIME + " = ?";
	
	private static final String QUERY_RESERVE_SEAT = "INSERT INTO " + TABLE_RESERVATION + "(" + COLUMN_RESERVATION_SEATID + " , " + COLUMN_RESERVATION_SHOWID +
													") VALUES(?, ?)";
	
	private static final String QUERY_CHECK_IF_RESERVED = "SELECT COUNT(*) FROM " + TABLE_RESERVATION + " WHERE " + COLUMN_RESERVATION_SEATID + " = ? AND " +
														  COLUMN_RESERVATION_SHOWID + " = ?"; 
	
	private static final String QUERY_SEATID = "SELECT " + COLUMN_SEATS_ID + " FROM " + TABLE_SEATS + " WHERE " + COLUMN_SEATS_SCREENNO + " = ? AND " +
												COLUMN_SEATS_ROWNO + " = ? AND " + COLUMN_SEATS_SEATNO + " = ?";
	
	private static final String QUERY_SHOWID = "SELECT " + COLUMN_SHOWS_ID + " FROM " + TABLE_SHOWS + " WHERE " + COLUMN_SHOWS_SCREENNO + " = ? AND " +
												COLUMN_SHOWS_DATE + " = ? AND " + COLUMN_SHOWS_TIME + " = ? AND " + COLUMN_SHOWS_MOVIEID + " = ?";
	
	
	public static void open()
	{
		try {
			connection = DriverManager.getConnection(CONNECTION_STRING);
		}catch(Exception e)
		{
			System.out.println("Error while opening DataBase: " + e);
		}
	}
	
	public static void close(Connection conn)
	{
		try {
			if((conn != null) && (!connection.isClosed()))
			{
				conn.close();				
			}
		}catch(SQLException e)
		{
			System.out.println("Unable to close connection : " + e);
		}
	}
	
	public static boolean addSeat(Seat seat)
	{
		while(isUpdating)
		{
			continue;
		}
		
		isUpdating = true;
		
		int lastScreenNo, lastRow;
		try
		{
			open();
			lastScreenNo = lastScreenNo();

			if((seat.getScreenNo() > lastScreenNo+1) || (seat.getScreenNo() <= 0))
			{
				return false;
			}
			
				lastRow = lastRowOf(seat.getScreenNo());
		
			if(lastRow == -1)
			{
				System.out.println("[!!] FATAL ERROR : Inconsistant DataBase!");
				return false;
			}
		
			if(seat.getScreenNo() == lastScreenNo+1)
			{
				return addSeatEntry(nextSeatID(), lastScreenNo+1, "A", 1, seat.getCategory(), seat.getPrice(), seat.isSpaceAfter());
			}
			else if((seat.getRowNo() == lastRow+1) && !(Character.toString((char) (lastRow+64)).equals("Z")))
			{
				return addSeatEntry(nextSeatID(), seat.getScreenNo(), Character.toString((char) (seat.getRowNo()+64)), 1, seat.getCategory(), seat.getPrice(), seat.isSpaceAfter());
			}
			else if((seat.getRowNo() < lastRow+1) && (seat.getRowNo() > 0))
			{
				return addSeatEntry(nextSeatID(), seat.getScreenNo(), Character.toString((char) (seat.getRowNo()+64)), lastSeatOf(seat.getScreenNo(), seat.getRowNo())+1, seat.getCategory(), seat.getPrice(),seat.isSpaceAfter());
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println("[!!] Unable to add new seat : " + e);
			return false;
		}
		finally
		{
			close(connection);
			isUpdating = false;
		}
		
	}
	
	private static boolean addSeatEntry(int seatID, int screenNo, String rowNo, int seatNo, String category, double price, boolean isSpaceAfter)
	{
		try {
			PreparedStatement statement = connection.prepareStatement(QUERY_ADDSEAT);
			statement.setInt(1, seatID);
			statement.setInt(2, screenNo);
			statement.setString(3, rowNo);
			statement.setInt(4, seatNo);
			statement.setString(5, category);
			statement.setDouble(6, price);
			statement.setBoolean(7, isSpaceAfter);
			
			statement.executeUpdate();
			statement.close();
			return true;
		}catch(SQLException e)
		{
			System.out.println("Error during adding seat Entry : "+ e);
			return false;
		}
	}
	
	private static int lastScreenNo() throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(QUERY_LAST_SCREEN);
		ResultSet results = statement.executeQuery();
		int ans = results.getInt(1);
		statement.close();
		return ans;
	}
	
	private static int lastRowOf(int screenNo) throws SQLException, NullPointerException
	{
		PreparedStatement statement = connection.prepareStatement(QUERY_LAST_ROW);
		statement.setInt(1, screenNo);
		ResultSet results = statement.executeQuery();
		String ans = results.getString(1);
		if(results.wasNull())
		{
			statement.close();
			return 0;
		}
		statement.close();
		char rowNo = ans.charAt(0);
		int ascii = (int) rowNo;
		if((ascii <= 90) && (ascii >= 65))
		{
			return ascii-64;
		}else
		{
			return -1;
		}
	}
	
	private static int nextSeatID() throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(QUERY_NEXT_SEATID);
		ResultSet results = statement.executeQuery();
		int ans = results.getInt(1) + 1;
		statement.close();
		return ans;
	}
	
	private static int lastSeatOf(int screenNo, int rowNo) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(QUERY_LAST_SEAT);
		statement.setInt(1, screenNo);
		statement.setString(2, Character.toString((char) (rowNo+64)));
		ResultSet results = statement.executeQuery();
		int ans = results.getInt(1);
		statement.close();
		return ans;
	}
		
	public static boolean addShow(Show show)
	{
		while(isUpdating)
		{
			continue;
		}
		
		isUpdating = true;
		
		try
		{
			open();
			if(!validateShow(show))
			{
				//System.out.println("[DEBUG] : Invalid Show!");
				return false;
			}
			//System.out.println("[DEBUG] : Adding Entry...");
			PreparedStatement statement = connection.prepareStatement(QUERY_ADDSHOW);
			statement.setInt(1, nextShowID());
			statement.setString(2, show.getShowDate());
			statement.setString(3, show.getShowTime());
			statement.setInt(4, show.getScreenNo());
			statement.setInt(5, show.getMovieID());
			
			statement.executeUpdate();
			statement.close();
			return true;
		}catch(SQLException e)
		{
			System.out.println("[!!] Error while adding new show : " + e);
			return false;
		}finally
		{
			close(connection);
			isUpdating = false;
		}
	}
	
	private static int nextShowID() throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(QUERY_NEXT_SHOWID);
		ResultSet results = statement.executeQuery();
		int ans = results.getInt(1) + 1;
		statement.close();
		return ans;
	}
	
	private static boolean validateShow(Show show) throws SQLException
	{
		if(!show.isValid())
		{
			//System.out.println("[DEBUG] : Invalid Show(2)!");
			return false;
		}
		
		/*
		 * PreparedStatement statement =
		 * connection.prepareStatement(QUERY_MOVIE_EXISTS); statement.setInt(1,
		 * show.getMovieID()); ResultSet results = statement.executeQuery(); int count =
		 * results.getInt(1); if(count == 0) {
		 * System.out.println("[DEBUG] : Movie Doesnot Exists!"); return false; } String
		 * releaseDt = results.getString(2); String[] dt = releaseDt.split("/", 3);
		 * LocalDate date = LocalDate.of(Integer.valueOf(dt[2]), Integer.valueOf(dt[1]),
		 * Integer.valueOf(dt[0]));
		 * if(date.isAfter(LocalDate.of(show.getDateTime().getYear(),
		 * show.getDateTime().getMonth(), show.getDateTime().getDay()))) {
		 * System.out.println("[DEBUG] : Invalid Date!"); results.close();
		 * statement.close(); return false; } results.close(); statement.close();
		 */
		
		if(!doesMovieExists(show) || !doesScreenExists(show.getScreenNo()) || doesShowExists(show))
		{
			return false;
		}
		
		return true;
	}
	
	private static boolean doesMovieExists(Show show) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(QUERY_MOVIE_EXISTS);
		statement.setInt(1, show.getMovieID());
		ResultSet results = statement.executeQuery();
		int count = results.getInt(1);
		if(count == 0)
		{
			//System.out.println("[DEBUG] : Movie Doesnot Exists!");
			results.close();
			statement.close();
			return false;
		}
		String releaseDt = results.getString(2);
		String[] dt = releaseDt.split("/", 3);
		LocalDate date = LocalDate.of(Integer.valueOf(dt[2]), Integer.valueOf(dt[1]), Integer.valueOf(dt[0]));
		if(date.isAfter(LocalDate.of(show.getDateTime().getYear(), show.getDateTime().getMonth(), show.getDateTime().getDay())))
		{
			//System.out.println("[DEBUG] : Invalid Date!");
			results.close();
			statement.close();
			return false;
		}
		results.close();
		statement.close();
		
		return true;
	}
	
	private static boolean doesScreenExists(int screenNo) throws SQLException
	{
		//System.out.println("[DEBUG] : Checking for screen...");
		PreparedStatement statement = connection.prepareStatement(QUERY_SCREEN_EXISTS);
		statement.setInt(1, screenNo);
		ResultSet results = statement.executeQuery();
		int count = results.getInt(1);
		results.close();
		statement.close();
		if(count == 0)
		{
			System.out.println("[DEBUG] : Screen Doesnot Exists!");
			return false;
		}
		
		return true;
	}
	
	private static boolean doesShowExists(Show show) throws SQLException
	{
		//System.out.println("[DEBUG] : Checking for show...");
		PreparedStatement statement = connection.prepareStatement(QUERY_SHOW_EXISTS);
		statement.setInt(1, show.getScreenNo());
		statement.setInt(2, show.getMovieID());
		statement.setString(3, show.getShowDate());
		statement.setString(4, show.getShowTime());
		ResultSet results = statement.executeQuery();
		int count = results.getInt(1);
		results.close();
		statement.close();
		if(count != 0)
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean changePrice(Seat seat)
	{
		while(isUpdating)
		{
			continue;
		}
		
		isUpdating = true;
		
		try
		{
			open();
			
			if((seat.getPrice() <= 0.0) || (seat.getScreenNo() > lastScreenNo()) || (seat.getRowNo() > lastRowOf(seat.getScreenNo())) ||
				(seat.getSeatNo() > lastSeatOf(seat.getScreenNo(), seat.getRowNo())) || (seat.getScreenNo() <= 0) || (seat.getRowNo() <= 0) ||
				(seat.getSeatNo() <= 0))
			{
				return false;
			}
			
			PreparedStatement statement = connection.prepareStatement(QUERY_CHANGE_PRICE);
			statement.setDouble(1, seat.getPrice());
			statement.setInt(2, seat.getScreenNo());
			statement.setString(3, Character.toString(seat.getCharRowNo()));
			statement.setInt(4, seat.getSeatNo());
			
			statement.executeUpdate();
			statement.close();
			return true;
		}catch(SQLException e)
		{
			System.out.println("[!!] Error while changing price : " + e);
			return false;
		}finally
		{
			close(connection);
			isUpdating = false;
		}
	}
	
	public static boolean addMovie(Movie movie)
	{
		return false;
	}
	
	public static boolean reserve(Seat seat, Show show)
	{
		while(isUpdating)
		{
			continue;
		}
		
		isUpdating = true;
		
		try
		{
			open();
			
			if((seat == null) || (show == null))
			{
				return false;
			}
			
			if((seat.getScreenNo() > lastScreenNo()) || (seat.getRowNo() > lastRowOf(seat.getScreenNo())) ||
				(seat.getSeatNo() > lastSeatOf(seat.getScreenNo(), seat.getRowNo())) || (seat.getScreenNo() <= 0) || (seat.getRowNo() <= 0) ||
				(seat.getSeatNo() <= 0))
			{
				System.out.println("[DEBUG] : Invalid Seat!");
				return false;
			}
			
			if(!doesShowExists(show) || !show.isValid())
			{
				System.out.println("[DEBUG] : Invalid Show!");
				return false;
			}
			
			if(seat.getScreenNo() != show.getScreenNo())
			{
				System.out.println("[DEBUG] : Seat & Show doesn't Match!");
				return false;
			}
			
			int seatId = seatIdOf(seat);
			int showId = showIdOf(show);
			
			if(isReserved(seatId, showId))
			{
				System.out.println("[DEBUG] : Seat already reserved!");
				return false;
			}
			
			PreparedStatement statement = connection.prepareStatement(QUERY_RESERVE_SEAT);
			statement.setInt(1, seatId);
			statement.setInt(2, showId);
			statement.executeUpdate();
			statement.close();
			
			return true;
			
		}catch(SQLException e)
		{
			System.out.println("Error while reserving seat! : " + e);
			return false;
		}
		finally
		{
			close(connection);
			isUpdating = false;
		}
	}
	
	private static boolean isReserved(int seatId, int showId) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(QUERY_CHECK_IF_RESERVED);
		statement.setInt(1, seatId);
		statement.setInt(2, showId);
		ResultSet results = statement.executeQuery();
		
		int count = results.getInt(1);
		results.close();
		statement.close();
		
		if(count == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private static int seatIdOf(Seat seat) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(QUERY_SEATID);
		statement.setInt(1, seat.getScreenNo());
		statement.setString(2, Character.toString((char)(64+seat.getRowNo())));
		statement.setInt(3, seat.getSeatNo());
		//ResultSet results = statement.executeQuery();
		int seatId = statement.executeQuery().getInt(1);
		//System.out.println("[DEBUG] : Got seatId!");

		//results.close();
		statement.close();
		
		return seatId;
	}
	
	private static int showIdOf(Show show) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(QUERY_SHOWID);
		statement.setInt(1, show.getScreenNo());
		statement.setString(2, show.getShowDate());
		statement.setString(3, show.getShowTime());
		statement.setInt(4, show.getMovieID());
		
		//ResultSet results = statement.executeQuery();
		int showId = statement.executeQuery().getInt(1);
		//results.close();
		statement.close();
		
		return showId;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
