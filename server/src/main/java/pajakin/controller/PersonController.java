package pajakin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pajakin.model.Person;
import pajakin.model.taxWrapper;
import pajakin.controller.Database;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5500")
@RestController
public class PersonController {

    @GetMapping("/person")
    public List<Person> getAll() {
        return Database.mongoOperation.findAll(Person.class);
    }

    @GetMapping("/person/{npwp}")
    public Person getOne(@PathVariable String npwp) {
        return Database.mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class);
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
        Database.mongoOperation.insert(p);
    }

    @PostMapping("/person/delete/{npwp}")
    public void deletePerson(@PathVariable String npwp) {
        Database.mongoOperation.findAndRemove(new Query(Criteria.where("_id").is(npwp)), Person.class);
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
        Person p = Database.mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class);
        p.setNIK(nik);
        p.setAllowance(allowance);
        p.setMarried(married);
        p.setFullName(fullname);
        p.setChildren(children);

        Database.mongoOperation.save(p);
    }

    @GetMapping("/person/{npwp}/tax/")
    public List<taxWrapper> getTax(@PathVariable String npwp) {
        Person p =  Database.mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class); 
        p.processOwnership();
        p.processSalary();

        return p.getTax();
    }
}