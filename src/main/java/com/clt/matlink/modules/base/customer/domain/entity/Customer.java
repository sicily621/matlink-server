package com.clt.matlink.modules.base.customer.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("hg_customer")
public class Customer extends BaseEntity {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Integer creditLevel;
    private String contactPerson;
}
