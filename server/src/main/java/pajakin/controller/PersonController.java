package pajakin.controller;


import java.util.List;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pajakin.model.Person;
import pajakin.controller.Database;;

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
}