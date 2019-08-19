package com.poc.kafkaclient.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PrimeIndex extends AbstractDto{
    private int prime_index;
}
