package myproject.service;

import myproject.model.BadStatus;
import myproject.model.Branches;
import myproject.repository.MyCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.stream.Collectors;

/**
 * @author paveldikin
 * @date 27.06.2020
 */
@Service
public class BranchesService {
    @Autowired
    MyCrudRepository repository;

    public ResponseEntity getById(Long id){
        Branches branches = repository.findBranchesById(id);
        if (branches != null){
            return ResponseEntity.ok(branches);
        }
        return ResponseEntity.badRequest().body(new BadStatus());
    }

    public ResponseEntity getNearest(Double lat, Double lon){
        List<Branches> allBranches =  repository.findAll();
        Branches branches = null;
        Double distance = Double.MAX_VALUE;
        for (Branches b : allBranches) {
            Double dist = getPathLength(b.getLat(), b.getLon(), lat, lon);
            if (dist < distance) {
                distance = dist;
                b.setDistance(dist.longValue());
                branches = b;
            }
        }
        System.out.println("return " + branches);
        return ResponseEntity.ok(branches);
    }


    private double getPathLength(double startLat, double startLon, double enddLat, double endLon) {
        double d2r = Math.PI / 180;
        double distance = 0;

        try {
            double dlong = (endLon - startLon) * d2r;
            double dlat = (enddLat - startLat) * d2r;
            double a =
                    Math.pow(Math.sin(dlat / 2.0), 2)
                            + Math.cos(startLat * d2r)
                            * Math.cos(enddLat * d2r)
                            * Math.pow(Math.sin(dlong / 2.0), 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double d = 6371 * c;

            return d;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
