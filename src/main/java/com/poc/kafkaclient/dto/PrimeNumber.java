package com.poc.kafkaclient.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrimeNumber extends AbstractDto{
    private long primeNumber;
}
