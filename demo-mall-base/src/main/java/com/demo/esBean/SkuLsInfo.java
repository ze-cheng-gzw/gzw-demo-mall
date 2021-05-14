package com.demo.esBean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class SkuLsInfo implements Serializable {

    @ApiModelProperty("商品Id")
    private String id;

    @ApiModelProperty("商品价格")
    private BigDecimal price;

    @ApiModelProperty("商品完整名称 --->  es名称搜索里分词")
    private String skuName;

    @ApiModelProperty("三级分类Id")
    private String catalog3Id;

    @ApiModelProperty("商品图片")
    private String skuDefaultImg;

    private List<SkuAttrValue> skuAttrValueList;
}
