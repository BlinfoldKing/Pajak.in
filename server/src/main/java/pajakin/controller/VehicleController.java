package pajakin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pajakin.model.Person;
import pajakin.model.Taxable;
import pajakin.model.Vehicle;
import pajakin.controller.Database;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@RestController
public class VehicleController {
    
    @GetMapping("/person/{npwp}/vehicle") 
    public List<Vehicle> getVehicle(@PathVariable String npwp) {
        Person p =  Database.mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class); 
        List<Vehicle> vList = new ArrayList<>(); 
        for (Taxable t: p.getOwnership()) {
            if (t instanceof Vehicle) {
                vList.add((Vehicle) t);
            }
        }
        return vList;
    }

    @PostMapping("/person/{npwp}/vehicle/add/{plateNumber}/{taxValue}")
    public void addVehicle(
        @PathVariable String npwp,
        @PathVariable String plateNumber,
        @PathVariable double taxValue
    ){
        Person p =  Database.mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class); 
        p.addOwnership((Taxable) new Vehicle(plateNumber, taxValue)); 

        Database.mongoOperation.save(p);
    }
}
