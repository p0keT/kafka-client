package com.poc.kafkaclient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.kafkaclient.dto.PrimeIndex;
import com.poc.kafkaclient.dto.PrimeNumber;
import com.poc.kafkaclient.model.Report;
import com.poc.kafkaclient.dto.ReportStatus;
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
    private final ObjectMapper objectMapper;
    private final List<Report> reports;
    @Autowired
    public PrimeNumberServiceImpl(KafkaTemplate<Long, PrimeIndex> kafkaPrimeIndexTemplate,
                                  ObjectMapper objectMapper) {
        this.kafkaPrimeIndexTemplate = kafkaPrimeIndexTemplate;
        this.objectMapper = objectMapper;
        reports = new ArrayList<>();
    }

    @Override
    public void produce(PrimeIndex primeIndex) {
        //выдправляється запит на звіт, створюється таск, створюється прайм індекс, заноситься в таск.
        log.info("<= sending {}", writeValueAsString(primeIndex));
        System.out.println(primeIndex.toString());
        this.reports.add(new Report(primeIndex, new PrimeNumber(), new ReportStatus()));
        kafkaPrimeIndexTemplate.send("report_requests", primeIndex);
    }

    @Override
    @KafkaListener(id = "report.status", topics = {"report_status"}, containerFactory = "singleReportStatusFactory")
    public void consume(ReportStatus reportStatus) {
        //отримується і оновлюється статус виконання звітів
        System.out.println("**********"+reportStatus.toString());
        Report currentReport = reports.stream()
                .filter(report -> report.getPrimeIndex().getId().equals(reportStatus.getId()))
                .findAny()
                .orElse(null);
        if(currentReport!=null){
            currentReport.setReportStatus(reportStatus);
        }else{
            //TODO: кинуть какой то рантайм
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
            //TODO: кинуть какой то рантайм
        }
    }

    private String writeValueAsString(PrimeIndex dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to JSON failed: " + dto.toString());
        }
    }

    public List<Report> getReports() {
        return new ArrayList<>(reports);
    }
}
