package myproject.service;

import myproject.model.BadStatus;
import myproject.model.Branches;
import myproject.model.Сongestion;
import myproject.repository.BranchesRepository;
import myproject.repository.ConguestionCrudRepository;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * @author paveldikin
 * @date 27.06.2020
 */
@Service
public class BranchesService {
    @Autowired
    BranchesRepository repository;
    @Autowired
    ConguestionCrudRepository congRepository;

    public ResponseEntity getById(Long id){
        if (id != null){
            Branches branches = repository.findBranchesById(id);
            if (branches != null){
                return ResponseEntity.ok(branches);
            }
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
                System.out.println("dist = " + dist);
                System.out.println(dist.longValue());
                b.setDistance(Math.round(1000.0 * dist));
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

    public ResponseEntity getPredicting(Long id, Integer dayOfWeek, Integer hourOfDay) {
        Branches b = repository.findBranchesById(id);
        if (dayOfWeek != null && hourOfDay != null && id != null && b != null){
            List<Сongestion> list = congRepository.findAll();
            Calendar c = Calendar.getInstance();
            List<Сongestion> sortedList = list.stream().filter(e -> {
//            System.out.println(e);
                c.setTime(e.getData());
                int day = c.get(Calendar.DAY_OF_WEEK);
//            System.out.println("day = " + day);

                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date(0));
//            System.out.println("before = " + cal.getTime());
                cal.add(Calendar.HOUR_OF_DAY, hourOfDay);
                Date d = cal.getTime();
//            System.out.println("after + date = " + d);

                return dayOfWeek.equals(day) && d.after(e.getStartTime()) && d.before(e.getFinishTime());
            }).collect(Collectors.toList());

            System.out.println("sorted list = " + sortedList);
            if (sortedList.size() > 0){
                List<Double> timelist = sortedList.stream().map(e -> {
                    System.out.println("start " + e.getStartTime());
                    System.out.println("finish " + e.getFinishTime());
                    double time = ((double) e.getFinishTime().getTime() - (double) e.getStartTime().getTime()) / 1000;
                    System.out.println("time = " + time);
                    return time;
                }).collect(Collectors.toList());

                double[] mas = new double[timelist.size()];
                for (int i = 0; i < timelist.size(); i++) {
                    mas[i] = timelist.get(i);
                }

                Median median = new Median();
                double medianValue = median.evaluate(mas);
                b.setDayOfWeek(dayOfWeek);
                b.setHourOfDay(hourOfDay);
                b.setPredicting(Math.round(medianValue));
                return ResponseEntity.ok(b);
            }
        }
        return ResponseEntity.badRequest().body(new BadStatus());
    }

    public double getMedian(double[] values){
        Median median = new Median();
        double medianValue = median.evaluate(values);
        return medianValue;
    }
}
