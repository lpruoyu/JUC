package programmer.lp.sxt.applications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Cinema {
    private String name; // 影院名称
    private volatile int tickSeats; // 影院剩余的座位数

    public Cinema(String name, int tickSeats) {
        this.name = name;
        this.tickSeats = tickSeats;
    }

    public boolean saleSeats(int seats) {
        if (seats < 1 || tickSeats < 1) {
            return false;
        }
        synchronized (this) {
            if (tickSeats >= seats) {
                tickSeats -= seats;
                return true;
            }
        }
        return false;
    }
}

class Customer extends Thread {
    private Cinema cinema; // 在哪个影院购票
    private int seats; // 购买多少张票

    public Customer(Cinema cinema, String name, int seats) {
        super(name);
        this.cinema = cinema;
        this.seats = seats;
    }

    @Override
    public void run() {
        if (cinema.saleSeats(seats)) {
            System.out.println(getName() + "成功购买了" + seats + "张票");
        } else {
            System.out.println(getName() + "购票失败，票数不够了");
        }
    }
}

class HiCinema {
    private String name; // 影院名称
    private volatile List<Integer> tickSeats; // 影院剩余的座位数

    public HiCinema(String name, List<Integer> tickSeats) {
        this.name = name;
        this.tickSeats = tickSeats;
    }

    public boolean saleSeats(List<Integer> seats) {
        if (seats == null || tickSeats == null || seats.isEmpty() || tickSeats.isEmpty()) {
            return false;
        }
        synchronized (this) {
            List<Integer> standBy = new ArrayList<>(tickSeats);
            standBy.removeAll(seats);
            if (tickSeats.size() - seats.size() == standBy.size()) {
                tickSeats = standBy;
                return true;
            }
        }
        return false;
    }
}

class HiCustomer extends Thread {
    private HiCinema cinema; // 在哪个影院购票
    private List<Integer> seats; // 购买多少张票

    public HiCustomer(HiCinema cinema, String name, List<Integer> seats) {
        super(name);
        this.cinema = cinema;
        this.seats = seats;
    }

    @Override
    public void run() {
        if (cinema.saleSeats(seats)) {
            System.out.println(getName() + "成功购买了" + seats.size() + "张票，位置：" + seats);
        } else {
            System.out.println(getName() + "购票失败");
        }
    }
}

public class HappyCinema {
    public static void main(String[] args) {
//        final Cinema cinema = new Cinema("快乐影院", 3);
//        new Customer(cinema, "高老师", 2).start();
//        new Customer(cinema, "裴老师", 2).start();

        List<Integer> allSeats = Arrays.asList(1, 2, 5, 9);
        List<Integer> seats1 = Arrays.asList(1, 2);
        List<Integer> seats2 = Arrays.asList(5);
        final HiCinema cinema = new HiCinema("快乐影院", allSeats);
        new HiCustomer(cinema, "高老师", seats1).start();
        new HiCustomer(cinema, "裴老师", seats2).start();
    }
}
