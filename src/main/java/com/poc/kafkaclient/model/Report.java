package com.poc.kafkaclient.model;

import com.poc.kafkaclient.dto.PrimeIndex;
import com.poc.kafkaclient.dto.PrimeNumber;
import com.poc.kafkaclient.dto.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Report {
    private PrimeIndex primeIndex;
    private PrimeNumber primeNumber;
    private ReportStatus reportStatus;


    public String getReport(){
        if(reportStatus.getReadiness_percentage()<100 && primeNumber.getPrimeNumber()==0L){
            return "Report ("+primeIndex.getId()+") for "+primeIndex.getPrime_index()+"'s prime number: is not completed yet!!! \n" +
                    "Readiness of the report is "+reportStatus.getReadiness_percentage()+"%";
        }else{
            return "Report ("+primeIndex.getId()+"): \n" +
                    primeIndex.getPrime_index()+"'s prime number is "+primeNumber.getPrimeNumber();
        }
    }
}
