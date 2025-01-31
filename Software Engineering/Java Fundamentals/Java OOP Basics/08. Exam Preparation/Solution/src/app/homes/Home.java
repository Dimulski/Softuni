package app.homes;

import app.items.Device;
import app.Room;
import app.people.Child;
import app.people.Person;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by RoYaL on 7/6/2016.
 */
public abstract class Home {

    private List<Room> rooms;
    private double budget;
    private double bills;
    private double salaries;

    protected List<Device> devices;
    protected List<Person> people;
    protected List<Child> children;

    Home() {
        this.rooms = new ArrayList<>();
        this.devices = new ArrayList<>();
        this.people = new ArrayList<>();
        this.children = new ArrayList<>();
        this.budget = 0;
        this.bills = 0;
        this.salaries = 0;
    }


    public boolean payBills() {
        if (this.budget >= this.bills) {
            this.budget -= this.bills;
            return true;
        }

        return false;
    }

    public void receiveSalary() {
        this.budget += this.salaries;
    }

    public int getPeopleCount() {
        return this.people.size() + this.children.size();
    }

    public double getConsumption() {
        return this.bills;
    }

    void calculateSalaries() {
        this.salaries = this.people.stream()
                .mapToDouble(Person::getIncome)
                .sum();
    }

    void calculateBills() {
        this.bills = this.rooms.stream()
                    .mapToDouble(Room::getConsumption)
                    .sum()
                +
                this.devices.stream()
                    .mapToDouble(Device::getConsumption)
                    .sum()
                +
                this.children.stream()
                    .mapToDouble(Child::getCost)
                    .sum();
    }

    void addRooms(int roomCount, double roomConsumption) {
        for (int i = 0; i < roomCount; i++) {
            this.rooms.add(new Room(roomConsumption));
        }
    }

    public abstract void addRooms();
}
