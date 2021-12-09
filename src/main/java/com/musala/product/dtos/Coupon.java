package com.musala.product.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class Coupon {

    private Long id;
    private String code;
    private BigDecimal discount;
    private String expDate;
}
