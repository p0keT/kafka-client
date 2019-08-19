package com.poc.kafkaclient.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReportStatus extends AbstractDto{
    private byte readiness_percentage;

    public ReportStatus() {
        readiness_percentage=0;
    }

    public void setReadiness_percentage(byte readiness_percentage) {
        if(readiness_percentage>100 || readiness_percentage<0)
            throw new IllegalArgumentException("Value should be between 0 and 100");
        this.readiness_percentage = readiness_percentage;
    }
}
