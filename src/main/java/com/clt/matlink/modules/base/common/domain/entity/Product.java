package com.clt.matlink.modules.base.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("hg_product")
public class Product extends BaseEntity {
        private Long id;
        private String code;
        private String name;
        private Long categoryId;
        private BigDecimal purchasePrice;
        private BigDecimal retailPrice;
        private String specification;
        private String unit;
        private String brand;
        private String barcode;
}
