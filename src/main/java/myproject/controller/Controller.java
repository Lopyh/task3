package myproject.controller;

import myproject.service.BranchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity getById(@PathVariable(required = false) Long id) {
        System.out.println("id = " + id);
        ResponseEntity resp = service.getById(id);
        System.out.println("return = " + resp);
        return resp;
    }

    @GetMapping("/branches")
    public ResponseEntity getNearest(@RequestParam Double lat, @RequestParam Double lon) {
        System.out.println("lat = " + lat + "; lon = " + lon);
        return service.getNearest(lat, lon);
    }

//     http://IP:8082/branches/{id}/predict?dayOfWeek=int&hourOfDay=int
    @GetMapping("/branches/{id}/predict")
    public ResponseEntity getPredicting(@PathVariable(required = false) Long id, @RequestParam(required = false) Integer dayOfWeek, @RequestParam(required = false) Integer hourOfDay) {
        System.out.println("id = " + id + "; dayOfWeek = " + dayOfWeek + "; hourOfDay = " + hourOfDay);

        return service.getPredicting(id, dayOfWeek, hourOfDay);
    }





}
