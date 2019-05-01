package cz.czechitas.webapp.controller;

import java.sql.*;
import java.util.*;
import org.mariadb.jdbc.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import cz.czechitas.webapp.domain.*;
import cz.czechitas.webapp.form.*;
import cz.czechitas.webapp.repository.*;

@Controller
public class HlavniController {

    CustomerRepository repository = new CustomerRepository();

    public HlavniController() {
    }

    @RequestMapping("/")
    public ModelAndView zobrazIndex() {
        ModelAndView dataProSablonu = new ModelAndView("index");
        return dataProSablonu;
    }

    @RequestMapping("/customers")
    public ModelAndView ukazSeznam(){
        ModelAndView seznam = new ModelAndView("customers/seznam");
        List<Customer> seznamVsech = repository.findAll();
        seznam.addObject("seznamZakazniku",seznamVsech);
        return seznam;
    }

    @RequestMapping("customers/new.html")
    public ModelAndView novyZaznam(){
        ModelAndView novy = new ModelAndView("customers/new");
        return novy;
    }

    @RequestMapping(value = "customers/new.html", method = RequestMethod.POST)
    public ModelAndView novyZakaznik(CustomerForm pridanyZakaznik){
        Customer novyZakaznik = new Customer(pridanyZakaznik.getFirstName(),pridanyZakaznik.getLastName(),
                pridanyZakaznik.getAddress(), pridanyZakaznik.isDeleted(),pridanyZakaznik.getVersion());
        ModelAndView novy = new ModelAndView("redirect:/customers");
        repository.save(novyZakaznik);
        return novy;
    }

    @RequestMapping("customers/{id}.html")
    public ModelAndView zobrazEditaci(@PathVariable("id") Long idCustomer){
        Customer zakaznik = repository.findOne(idCustomer);
        ModelAndView edit = new ModelAndView("customers/edit");
        edit.addObject("customer",zakaznik);
        return edit;
    }

    @RequestMapping(value = "customers/{id}.html", method = RequestMethod.POST)
    public ModelAndView edituj(@PathVariable("id") Long idCustomer, CustomerForm editovany){
        Customer zakaznik = new Customer(idCustomer,editovany.getFirstName(),editovany.getLastName(),
                editovany.getAddress(),editovany.isDeleted(),editovany.getVersion());
        ModelAndView editZakaznika = new ModelAndView("redirect:/customers");
        repository.save(zakaznik);
        return editZakaznika;
    }

    @RequestMapping(value = "customers/delete/{id}", method = RequestMethod.GET)
    public ModelAndView odstran(@PathVariable("id") Long idCustomer){
     Customer zakaznik = repository.findOne(idCustomer);
     ModelAndView smazat = new ModelAndView("redirect:/customers");
     repository.delete(zakaznik.getId(),zakaznik.getVersion());
     return smazat;
    }
}
