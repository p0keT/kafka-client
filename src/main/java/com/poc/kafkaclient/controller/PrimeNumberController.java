package com.poc.kafkaclient.controller;

import com.poc.kafkaclient.dto.PrimeIndex;
import com.poc.kafkaclient.model.Report;
import com.poc.kafkaclient.service.PrimeNumberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class PrimeNumberController {

    private final PrimeNumberServiceImpl service;

    @Autowired
    public PrimeNumberController(PrimeNumberServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public String report(Map<String, Object> model) {
        return "report";
    }

    @PostMapping("/new")
    public String send(@RequestParam int primeIndex) {

        //TODO: глянути, може можна замапити сам об'єкт, а не int
        PrimeIndex pi = new PrimeIndex();
        pi.setPrime_index(primeIndex);
        pi.setCreated(LocalDateTime.now());
        pi.setId(UUID.randomUUID().toString());
        service.produce(pi);
        return "report";
    }

    @GetMapping("/status")
    public String status(Model model) {
        //model.put("status",service.getCurrentReportStatus().getReadiness_percentage());
        List<Report> reports = service.getReports();
        List<String> stringReports = reports.stream().map(report -> report.getReport()).collect(Collectors.toList());
        model.addAttribute("reports",stringReports);
        return "status";
    }

}
