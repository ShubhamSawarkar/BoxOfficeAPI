# BoxOfficeAPI
A REST API for online movie ticket booking platforms, managed by a multiplex.

This project opens an API for online ticket booking platforms like, the well known, "BookMyShow". The API is controllable by the multiplex owners by means of a graphical user interface
providing them with a rich set of options for performing their daily taks.

The REST API provides a web interface for admins which will appear only after logging in as admin.
The web interface provides graphical interface to admins that allows them to:

1. Add new shows.
2. View Reservation status for various shows.
3. Add new Movie.
4. Add new API user.
5. Set privilages of an user (user or admin).
6. Change price and category of different seats.
7. Manage seats and their position within hall.
8. Manage spaces between individual seats and rows of seats.

Normal API consumer users can use following mappings for performing specific tasks:

1. /getSeats: Returns all the seats within a perticular hall. Takes an integer as argument called "screen" specifying the screen number whose seats to be fetched.
              The JSON objects returned contains information about each seat such as its ID, row number, screen number, price, category, etc.
              
2. /reserve: Returns the JSON object for the seat if the specified seat reserved successfully or an JSON object specifying reason for which the seat could not be reserved.
             Takes four integer arguments: screen (screen number), row (row number within that screen hall), seat (seat number within that row) and show (the show ID of the show for which the seat is to be reserved).
             
3. /getShows: Return a list of JSON objects specifiying for each show its show ID, movie ID, show time, show date and screen on which show is to be held.

4. /getMovies: Returns a list of JSON objects specifying for each movie its movie ID, title, runtime, etc.

5. /getStatus: Takes an integer as argument naming "show-id" specifying the show ID of which status is to be obtained. Returns a list of JSON objects specifying for each seat in the 
               corresponding screen hall whether it is reserved for the given show or not.
               
Technologies Used:

1. Spring Boot.
2. Spring Security for user authentication.
3. JavaServer Pages (JSP) for web interface.
4. Client-side technologies like HTML, CSS and JQuery for User interface.
