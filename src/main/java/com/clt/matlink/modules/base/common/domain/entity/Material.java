package com.clt.matlink.modules.base.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("clt_material")
public class Material extends BaseEntity {
        private Long id;
        private Long materialTypeId;
        private String code;
        private String name;
        private String simpleName;
        private String brand;
        private String modelNo;
        private String specification;
        private Long unit;
        private String producingArea;
        private String description;
        private Integer minCountLimit;
        private Integer maxCountLimit;
        private Integer safeCountLimit;
        private BigDecimal suggestedCostPrice;
        private BigDecimal suggestedPurchasePrice;
        private Integer status;
        private String barcode;
        private Long createUserId;
}
