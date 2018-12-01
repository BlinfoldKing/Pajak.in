package pajakin.controller;


import java.util.List;

import java.util.concurrent.atomic.AtomicLong;

import javax.xml.crypto.Data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pajakin.model.Person;
import pajakin.model.Tax;
import pajakin.model.Taxable;
import pajakin.model.Vehicle;
import pajakin.controller.Database;

import java.util.List;
import java.util.ArrayList;

@RestController
public class PersonController {

    @GetMapping("/person")
    public List<Person> getAll() {
        return Database.personList;
    }

    @PostMapping("/person/add/{fullname}/{nik}/{npwp}/{salary}/{allowance}/{married}/{children}")
    public void addPerson(
        @PathVariable String fullname,
        @PathVariable String nik,
        @PathVariable String npwp,
        @PathVariable double salary,
        @PathVariable double allowance,
        @PathVariable boolean married,
        @PathVariable int children
    ) {
        Database.personList.add(new Person(fullname, nik, npwp, salary, allowance, married, children));
    }

    @PostMapping("/person/delete/{npwp}")
    public void deletePerson(@PathVariable String npwp) {
        List<Person> newList = new ArrayList<Person>();
        for (Person p: Database.personList) {
            if (!npwp.matches(p.getNPWP())) {
                System.out.println(npwp);
                System.out.println(p.getNPWP());
                newList.add(p);
            }
        }
        Database.personList = newList;
    }

    @PostMapping("/person/edit/{fullname}/{nik}/{npwp}/{salary}/{allowance}/{married}/{children}")
    public void editPerson(
        @PathVariable String fullname,
        @PathVariable String nik,
        @PathVariable String npwp,
        @PathVariable double salary,
        @PathVariable double allowance,
        @PathVariable boolean married,
        @PathVariable int children
    ) {
        for (Person p: Database.personList) {
            if (npwp.matches(p.getNPWP())) {
                Database.personList.remove(p);
                Database.personList.add(new Person(fullname, nik, npwp, salary, allowance, married, children));
                break;
            }
        }
    }

    @GetMapping("/person/{npwp}/vehicle")
    public List<Vehicle> getVehicle(@PathVariable String npwp){
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        for (Person p: Database.personList) {
            if (npwp.matches(p.getNPWP())) {
                for (Taxable t: p.getOwnership()) {
                    if (t instanceof Vehicle) {
                        vehicleList.add((Vehicle) t);
                    }
                }
                break;
            }
        }
        return vehicleList;
    }

    @GetMapping("/person/{npwp}/vehicle/add/{plateNumber}/{taxValue}")
    public void addVehicle(
        @PathVariable String npwp,
        @PathVariable String plateNumber,
        @PathVariable double taxValue
    ){
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        for (Person p: Database.personList) {
            if (npwp.matches(p.getNPWP())) {
                p.addOwnership((Taxable) new Vehicle(plateNumber, taxValue));                
            }
        }
    }
}