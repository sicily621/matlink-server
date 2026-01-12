package com.clt.matlink.modules.base.supplier.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("clt_supplier_information")
public class Supplier extends BaseEntity {
    private Long id;
    private String supplierNo;
    private String simpleNo;
    private String name;
    private String address;
    private String mainBusiness;
    private Integer level;
    private String linkMan;
    private String phone;
    private String fax;
    private String email;
    private Integer status;
    private String description;
}
