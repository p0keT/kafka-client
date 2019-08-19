package com.poc.kafkaclient.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PrimeNumber extends AbstractDto{
    private long primeNumber;
}
