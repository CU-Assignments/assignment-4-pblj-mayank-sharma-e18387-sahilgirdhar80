import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TicketBookingSystem {
    private final List<Boolean> seats; 
    private final Lock lock; 

    public TicketBookingSystem(int totalSeats) {
        seats = new ArrayList<>(totalSeats);
        for (int i = 0; i < totalSeats; i++) {
            seats.add(false); 
        }
        lock = new ReentrantLock();
    }

    public void bookSeat(int seatNumber, String customerType) {
        lock.lock(); 
        try {
            if (seatNumber < 0 || seatNumber >= seats.size()) {
                System.out.println("Invalid seat number: " + seatNumber);
                return;
            }
            if (!seats.get(seatNumber)) {
                seats.set(seatNumber, true); 
                System.out.println(customerType + " booked seat number: " + seatNumber);
            } else {
                System.out.println("Seat number " + seatNumber + " is already booked.");
            }
        } finally {
            lock.unlock(); 
        }
    }
}

class BookingThread extends Thread {
    private final TicketBookingSystem bookingSystem;
    private final int seatNumber;
    private final String customerType;

    public BookingThread(TicketBookingSystem bookingSystem, int seatNumber, String customerType) {
        this.bookingSystem = bookingSystem;
        this.seatNumber = seatNumber;
        this.customerType = customerType;
    }

    @Override
    public void run() {
        bookingSystem.bookSeat(seatNumber, customerType);
    }
}

public class TicketBookingSystemDemo {
    public static void main(String[] args) {
        TicketBookingSystem bookingSystem = new TicketBookingSystem(10); 

        BookingThread vip1 = new BookingThread(bookingSystem, 2, "VIP Customer 1");
        BookingThread vip2 = new BookingThread(bookingSystem, 3, "VIP Customer 2");
        BookingThread vip3 = new BookingThread(bookingSystem, 2, "VIP Customer 3"); 
        BookingThread regular1 = new BookingThread(bookingSystem, 2, "Regular Customer 1"); 
        BookingThread regular2 = new BookingThread(bookingSystem, 4, "Regular Customer 2");

        vip1.setPriority(Thread.MAX_PRIORITY);
        vip2.setPriority(Thread.MAX_PRIORITY);
        vip3.setPriority(Thread.MAX_PRIORITY);
        regular1.setPriority(Thread.NORM_PRIORITY);
        regular2.setPriority(Thread.NORM_PRIORITY);

        vip1.start();
        vip2.start();
        vip3.start();
        regular1.start();
        regular2.start();

        try {
            vip1.join();
            vip2.join();
            vip3.join();
            regular1.join();
            regular2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Booking process completed.");
    }
}
