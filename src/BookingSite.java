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
	private String startDate;
	private String endDate;
	
	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Booking( String username, String roomId, String startDate, String endDate) {
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
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
		System.out.println("3. To cancel a booking");
		System.out.println("4. To exit Hotel Reservation");
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
		System.out.println("Bookings for " + un);
		System.out.println("BookingID	Booking Room	Start Date	EndDate");
		System.out.println("---------------------------------------------------------------");
		for(Booking b:ab) {
			if(b.getUsername().equals(un)) {
				String roomName = getRoomByID(ar, b.getRoomId());
				System.out.println(b.getBookingId()+"		"+ roomName +"		"+ b.getStartDate() + "	" + b.getEndDate());
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

	ArrayList<Booking> bookRoom(ArrayList<Booking> ab, ArrayList<Room> ah, String un) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Room ID		Rooms Available");
		for(Room r:ah) {
			System.out.println(r.getRoomID()+"			"+r.getRoomName());
		}
		System.out.print("Enter the room id you would like to book : ");
		String room = scan.next();
		
		System.out.print("Please enter the start date you would like to book (dd/mm/yyyy) : ");
		String startDate = scan.next();
		
		System.out.print("Please enter the end date you would like to book (dd/mm/yyyy) : ");
		String endDate = scan.next();
		
		Booking b = new Booking(un, room, startDate, endDate);
		ab.add(b);
		System.out.println("Your booking has been added!");
		return ab;
		
	}
	
	ArrayList<Booking> removeBooking(ArrayList<Booking> ab, ArrayList<Room> ah, String un) {
		Scanner scan = new Scanner(System.in);
		
		int count = checkBooking(ab, ah, un);

		if(count > 0) {
			System.out.print("Enter the booking id you want to cancel : ");
			int bookingId = scan.nextInt();
			for(Booking b:ab) {
				if(b.getBookingId() == bookingId) {
					ab.remove(b);
					System.out.println("Booking has been cancelled!");
					return ab;
				}
			}
		}
		return null;
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

		Room r1 = new Room("1234","Room A");
		ar.add(r1);
		Room r2 = new Room("4321","Room B");
		ar.add(r2);
		Room r3 = new Room("1235","Room C");
		ar.add(r3);

		Booking b1 = new Booking("un1","1234","25/04/2021", "26/04/2021");
		ab.add(b1);
		Booking b2 = new Booking("un2","4321","25/04/2021", "26/04/2021");
		ab.add(b2);

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
							ab = bookRoom(ab, ar, u);
							break;
						case 3:
							ab = removeBooking(ab, ar, u);
							break;
						case 4:
							exit = true;
							break;
						default:
							System.out.println("Invalid Option!");
					}
				}
			}
			else {
				System.out.println("Invalid Login! Attempted " + (attempt+1) + "/3" );
				attempt ++;
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
