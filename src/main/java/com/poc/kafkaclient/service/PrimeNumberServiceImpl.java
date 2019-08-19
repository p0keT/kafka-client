package com.poc.kafkaclient.service;

import com.poc.kafkaclient.dto.PrimeIndex;
import com.poc.kafkaclient.dto.PrimeNumber;
import com.poc.kafkaclient.dto.ReportStatus;
import com.poc.kafkaclient.model.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PrimeNumberServiceImpl implements PrimeNumberService {

    private final KafkaTemplate<Long, PrimeIndex> kafkaPrimeIndexTemplate;
    private final List<Report> reports;

    @Autowired
    public PrimeNumberServiceImpl(KafkaTemplate<Long, PrimeIndex> kafkaPrimeIndexTemplate) {
        this.kafkaPrimeIndexTemplate = kafkaPrimeIndexTemplate;
        reports = new ArrayList<>();
    }

    @Override
    public void produce(PrimeIndex primeIndex) {
        log.info("<= sending {}", primeIndex);
        this.reports.add(new Report(primeIndex, new PrimeNumber(), new ReportStatus()));
        kafkaPrimeIndexTemplate.send("report_requests", primeIndex);
    }

    @Override
    @KafkaListener(id = "report.status", topics = {"report_status"}, containerFactory = "singleReportStatusFactory")
    public void consume(ReportStatus reportStatus) {
        Report currentReport = reports.stream()
                .filter(report -> report.getPrimeIndex().getId().equals(reportStatus.getId()))
                .findAny()
                .orElse(null);
        if(currentReport!=null){
            currentReport.setReportStatus(reportStatus);
        }else{
            log.warn("You get the status of a non-existent report.");
        }
    }

    @Override
    @KafkaListener(id = "prime.number.report", topics = {"completed_reports"}, containerFactory = "singlePrimeNumberFactory")
    public void consume(PrimeNumber primeNumber) {
        System.out.println("PrimeNumber: "+primeNumber.toString());
        Report currentReport = reports.stream()
                .filter(report -> report.getPrimeIndex().getId().equals(primeNumber.getId()))
                .findAny()
                .orElse(null);
        if(currentReport!=null){
            currentReport.setPrimeNumber(primeNumber);
        }else{
            log.warn("You get the prime number of a non-existent report.");
        }
    }

    public List<Report> getReports() {
        return new ArrayList<>(reports);
    }
}
