package pajakin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pajakin.model.Person;
import pajakin.model.Taxable;
import pajakin.model.Property;
import pajakin.controller.Database;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5500")
@RestController
public class PropertyController {

    @GetMapping("/person/{npwp}/property")
    public List<Property> getProperty(@PathVariable String npwp){
        Person p =  Database.mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class); 
        List<Property> pList = new ArrayList<>(); 
        for (Taxable t: p.getOwnership()) {
            if (t instanceof Property) {
                pList.add((Property) t);
            }
        }
        return pList;
    }

    @PostMapping("/person/{npwp}/property/add/{address}/{landArea}/{landSaleValue}/{buildingArea}/{buildingSaleValue}")
    public void addProperty(
        @PathVariable String npwp,
        @PathVariable String address,
        @PathVariable double landArea,
        @PathVariable double landSaleValue,
        @PathVariable double buildingArea,
        @PathVariable double buildingSaleValue
    ){

        Person p =  Database.mongoOperation.findOne(new Query(Criteria.where("_id").is(npwp)), Person.class); 
        p.addOwnership((Taxable) new Property(address, landArea, landSaleValue, buildingArea, buildingSaleValue));
        Database.mongoOperation.save(p);
    }
}
