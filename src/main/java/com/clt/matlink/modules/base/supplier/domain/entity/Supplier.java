package com.clt.matlink.modules.base.supplier.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("hg_supplier")
public class Supplier extends BaseEntity {
    private Long id;
    private String code;
    private String name;
    private String contactPerson;
    private String phone;
    private String address;
    private String email;
}
