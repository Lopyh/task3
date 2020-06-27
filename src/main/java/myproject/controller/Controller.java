package myproject.controller;

import myproject.model.BadStatus;
import myproject.model.Branches;
import myproject.repository.MyCrudRepository;
import myproject.service.BranchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author paveldikin
 * @date 25.06.2020
 */
@RestController
public class Controller {

    @Autowired
    BranchesService service;

    String status = "branch not found";

    @GetMapping("/branches/{id}")
    public ResponseEntity greeting(@PathVariable Long id) {
        System.out.println("id = " + id);
        ResponseEntity resp = service.getById(id);
        System.out.println("return = " + resp);
        return resp;
    }

    @GetMapping("/branches")
    public ResponseEntity greeting(@RequestParam Double lat, @RequestParam Double lon) {
        System.out.println("lat = " + lat + "; lon = " + lon);
        return service.getNearest(lat, lon);
    }



}
