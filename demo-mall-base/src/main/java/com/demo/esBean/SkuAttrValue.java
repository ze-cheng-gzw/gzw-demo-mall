package com.demo.esBean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SkuAttrValue implements Serializable {

    private String valueId;
}
