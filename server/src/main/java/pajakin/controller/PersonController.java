package pajakin.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pajakin.model.Person;
import pajakin.model.Taxable;
import pajakin.model.Vehicle;
import pajakin.model.taxWrapper;
import pajakin.model.Property;
import pajakin.controller.Database;

import java.util.List;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@RestController
public class PersonController {

    ApplicationContext ctx = 
        new AnnotationConfigApplicationContext(Database.class);
    MongoOperations mongoOperation = 
        (MongoOperations) ctx.getBean("mongoTemplate");

    @GetMapping("/person")
    public List<Person> getAll() {
        return mongoOperation.findAll(Person.class);
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
        Person p = new Person(fullname, nik, npwp, salary, allowance, married, children);
        mongoOperation.insert(p);
    }

    @PostMapping("/person/delete/{npwp}")
    public void deletePerson(@PathVariable String npwp) {
        mongoOperation.findAndRemove(new Query(Criteria.where("_id").is(npwp)), Person.class);
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
        Person p = mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class);
        p.setNIK(nik);
        p.setAllowance(allowance);
        p.setMarried(married);
        p.setFullName(fullname);
        p.setChildren(children);

        mongoOperation.save(p);
    }

    @GetMapping("/person/{npwp}/vehicle") 
    public List<Vehicle> getVehicle(@PathVariable String npwp) {
        Person p =  mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class); 
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
        Person p =  mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class); 
        p.addOwnership((Taxable) new Vehicle(plateNumber, taxValue)); 

        mongoOperation.save(p);
    }

    @GetMapping("/person/{npwp}/property")
    public List<Property> getProperty(@PathVariable String npwp){
        Person p =  mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class); 
        List<Property> pList = new ArrayList<>(); 
        for (Taxable t: p.getOwnership()) {
            if (t instanceof Property) {
                pList.add((Property) t);
            }
        }
        return pList;
    }

    @GetMapping("/person/{npwp}/vehicle/add/{address}/{landArea}/{landSaleValue}/{buildingArea}/{buildingSaleValue}")
    public void addProperty(
        @PathVariable String npwp,
        @PathVariable String address,
        @PathVariable double landArea,
        @PathVariable double landSaleValue,
        @PathVariable double buildingArea,
        @PathVariable double buildingSaleValue
    ){

        Person p =  mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class); 
        p.addOwnership((Taxable) new Property(address, landArea, landSaleValue, buildingArea, buildingSaleValue));
        mongoOperation.save(p);
    }

    @GetMapping("/person/{npwp}/tax/")
    public List<taxWrapper> getTax(@PathVariable String npwp) {
        Person p =  mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class); 
        p.processOwnership();
        p.processSalary();

        return p.getTax();
    }
}