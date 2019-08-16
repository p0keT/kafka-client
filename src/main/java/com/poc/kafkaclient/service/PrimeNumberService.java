package com.poc.kafkaclient.service;

import com.poc.kafkaclient.dto.PrimeIndex;
import com.poc.kafkaclient.dto.PrimeNumber;
import com.poc.kafkaclient.dto.ReportStatus;

public interface PrimeNumberService {

    void produce(PrimeIndex primeIndex);
    void consume(ReportStatus reportStatus);
    void consume(PrimeNumber primeNumber);

}
