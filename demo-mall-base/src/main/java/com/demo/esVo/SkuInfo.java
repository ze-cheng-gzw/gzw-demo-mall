package com.demo.esVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("商品信息")
public class SkuInfo implements Serializable {

    @ApiModelProperty("对应的商品id")
    private Long id;

    @ApiModelProperty("商品价格")
    private String price;

    @ApiModelProperty("商品名称")
    private String skuName;

    @ApiModelProperty("3级分类id")
    private Long catalog3Id;

    @ApiModelProperty("图片地址")
    private String skuDefaultImg;

    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZ",timezone="GMT+8")
    private Date create_time;


}
