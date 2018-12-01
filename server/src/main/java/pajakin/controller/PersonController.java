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
}