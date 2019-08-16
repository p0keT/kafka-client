package com.poc.kafkaclient.dto;

import lombok.*;

@Data
public class ReportStatus extends AbstractDto{
    private byte readiness_percentage;

    public ReportStatus() {
        readiness_percentage=0;
    }
}
