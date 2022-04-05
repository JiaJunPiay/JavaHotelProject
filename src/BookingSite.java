import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class Customer{
	private String username;
	private String password;
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [username=" + username + ", password=" + password + "]";
	}

	public boolean checkValidUser(String u, String p) {
		if(username.equals(u) && password.equals(p)) {
			return true;
		}
		return false;
	}
}

class Room{
	private String roomID;
	private String roomName;
	private float roomRate;

	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Room(String roomID, String roomName) {
		super();
		this.roomID = roomID;
		this.roomName = roomName;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
}

class Booking{
	private static final AtomicInteger count = new AtomicInteger(1); 
	private int bookingId;
	private String username;
	private String roomId;
	private Date startDate;
	private Date endDate;
	
	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Booking( String username, String roomId, Date startDate, Date endDate) {
		bookingId = count.getAndIncrement();
		this.username = username;
		this.roomId = roomId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getBookingId() {
		return bookingId;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", username=" + username + ", roomId=" + roomId + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}

}

class Site{
	void printMenu() {
		System.out.println("1. To check your booking");
		System.out.println("2. To book a room");
		System.out.println("3. To edit a booking");
		System.out.println("4. To cancel a booking");
		System.out.println("5. To exit Hotel Reservation");
	}
	
	boolean loginCheck(ArrayList<Customer> ac, String u, String p) {
		for(Customer a:ac) {
			Boolean valid = a.checkValidUser(u, p);
			if(valid)
				return true;
		}
		return false;
	}
	
	int checkBooking(ArrayList<Booking> ab,  ArrayList<Room> ar, String un) {
		int count = 0;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		System.out.println("Bookings for " + un);
		System.out.println("BookingID	Booking Room	Start Date	End Date");
		System.out.println("---------------------------------------------------------------");
		for(Booking b:ab) {
			if(b.getUsername().equals(un)) {
				String roomName = getRoomByID(ar, b.getRoomId());
				System.out.println(b.getBookingId()+"		"+ roomName +"		"+ formatter.format(b.getStartDate()) + "	" + formatter.format(b.getEndDate()));
				count++;
			}
		}
		if(count == 0) {
			System.out.println("You have no booking currently!");
		}
		System.out.println();
		
		return count;
	}

	String getRoomByID(ArrayList<Room> ar, String id) {
		for(Room r:ar) {
			if(r.getRoomID().equals(id)) {
				return r.getRoomName();
			}
		}
		return null;
	}

	void bookRoom(ArrayList<Booking> ab, ArrayList<Room> ah, String un) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Room ID		Rooms Available");
		for(Room r:ah) {
			System.out.println(r.getRoomID()+"			"+r.getRoomName());
		}
		System.out.print("Enter the room id you would like to book : ");
		String room = scan.next();
		String checkValid = getRoomByID(ah, room);
		while(checkValid == null) {
			System.out.print("Invalid room id, Please try again : ");
			room = scan.next();
			checkValid = getRoomByID(ah, room);
		}
		
		try {
			
			System.out.print("Please enter the start date you would like to book (dd/mm/yyyy) (01/01/2000)  : ");
			String startDateString = scan.next();
			Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDateString);
			
			while(startDate.before(new Date()) || startDate.equals(new Date())) {
				System.out.print("Invalid start date! Please enter the end date you would like to book (dd/mm/yyyy) (01/01/2000) : ");
				startDateString = scan.next();
				startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDateString); 
			}
			
			System.out.print("Please enter the end date you would like to book (dd/mm/yyyy) (01/01/2000) : ");
			String endDateString = scan.next();
			Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDateString); 
			
			while(endDate.before(startDate) || endDate.equals(startDate)) {
				System.out.print("Invalid end date! Please enter the end date you would like to book (dd/mm/yyyy) (01/01/2000) : ");
				endDateString = scan.next();
				endDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDateString); 
			}
			
			Booking b = new Booking(un, room, startDate, endDate);
			ab.add(b);
			System.out.println("Your booking has been added!");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid Date Format! Please try again!");
			//e.printStackTrace();
		}  
		
	}
	
	void editBooking(ArrayList<Booking> ab, ArrayList<Room> ah, String un) {
		Scanner scan = new Scanner(System.in);
		int count = checkBooking(ab, ah, un);
		try {
			if(count > 0) {
				System.out.print("Enter booking id you would like to edit : ");
				int id = scan.nextInt();
				int bookCount = 0;
				for(Booking b:ab) {
					if(b.getBookingId() == id && b.getUsername().equals(un)) {
						
						System.out.print("Please enter the start date you would like to edit (dd/mm/yyyy) (01/01/2000)  : ");
						String startDateString = scan.next();
						Date startDate;
						
							startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDateString);
						
						
						while(startDate.before(new Date()) || startDate.equals(new Date())) {
							System.out.print("Invalid start date! Please enter the end date you would like to book (dd/mm/yyyy) (01/01/2000) : ");
							startDateString = scan.next();
							startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDateString); 
						}
						
						System.out.print("Please enter the end date you would like to book (dd/mm/yyyy) (01/01/2000) : ");
						String endDateString = scan.next();
						Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDateString); 
						
						while(endDate.before(startDate) || endDate.equals(startDate)) {
							System.out.print("Invalid end date! Please enter the end date you would like to book (dd/mm/yyyy) (01/01/2000) : ");
							endDateString = scan.next();
							endDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDateString); 
						}
						
						b.setStartDate(startDate);
						b.setEndDate(endDate);
						
						break;
						
					}
					else {
						bookCount++;
					}
				}
				
				if(bookCount == ab.size())
					System.out.println("You do not have such booking. Please try again");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid Date Format! Please try again!");
			e.printStackTrace();
		}
	}
	
	
	
	void removeBooking(ArrayList<Booking> ab, ArrayList<Room> ah, String un) {
		Scanner scan = new Scanner(System.in);
		
		int count = checkBooking(ab, ah, un);
		try {
			boolean remove = false;
			if(count > 0) {
				System.out.print("Enter the booking id you want to cancel : ");
				int bookingId = scan.nextInt();
				for(Booking b:ab) {
					if(b.getBookingId() == bookingId) {
						ab.remove(b);
						remove = true;
					}
				}
			}
			if(remove) {
				System.out.println("Booking has been cancelled!");
			}
			else {
				System.out.println("Booking id does not exist!!");
			}

		}
		catch(Exception e) {
			System.out.println("Error with the removal of booking!");
		}
	}

	void startSite() {
		ArrayList<Customer> ac = new ArrayList<Customer>();
		ArrayList<Room> ar = new ArrayList<Room>();
		ArrayList<Booking> ab = new ArrayList<Booking>();
		Scanner scan = new Scanner(System.in);

		Customer c1 = new Customer("un1", "pw1");
		ac.add(c1);
		Customer c2 = new Customer("un2", "pw2");
		ac.add(c2);
		Customer c3 = new Customer("un3", "pw3");
		ac.add(c3);


		Room r1 = new Room("1234","Room A");
		ar.add(r1);
		Room r2 = new Room("4321","Room B");
		ar.add(r2);
		Room r3 = new Room("1235","Room C");
		ar.add(r3);
		
		DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date d1 = formatter1.parse("26/05/2021");
			Date d2 = formatter1.parse("27/05/2021");
			
			Booking b1 = new Booking("un1","1234",d1, d2);
			ab.add(b1);
			Booking b2 = new Booking("un2","4321", d1, d2);
			ab.add(b2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Welcome to Hotel Reservation!");
		int attempt = 0;
		Boolean valid = false;

		while(attempt < 3 && valid == false) {
			System.out.print("Please enter your username : ");
			String u = scan.next();
			System.out.print("Please enter your password : ");
			String p = scan.next();
			
			valid = loginCheck(ac, u, p);
			
			if(valid) {
				boolean exit = false;
				while(!exit) {
					int option = 0;
					printMenu();
					System.out.print("Select your option : ");
					option = scan.nextInt();
					switch(option) {
						case 1:
							checkBooking(ab, ar, u);
							break;
						case 2:
							bookRoom(ab, ar, u);
							break;
						case 3:
							editBooking(ab, ar, u);
							break;
						case 4:
							removeBooking(ab, ar, u);
							break;
						case 5:
							exit = true;
							break;
						default:
							System.out.println("Invalid Option!");
					}
				}
			}
			else {
				attempt++;
				System.out.println("Invalid Login! Attempted " + attempt + "/3" );
				
			}
		}
		System.out.println("System exited.... Goodbye!");
	}
}

public class BookingSite {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Site s = new Site();
		s.startSite();
	}
}
