package com.clt.matlink.modules.outstock.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName("clt_material_out_stock")
public class OutStock extends BaseEntity {
    private Long id;
    private Long originOrderId;
    private Long stockId;
    private String outStockNo;
    private Integer auditStatus;
    private Integer status;
    @Schema(description = "出库类型：1-领料出库，2-退货入库，3-报废出库，4-其它出库")
    private Integer type;
    private Long deptId;
    private String description;
    private Integer isDirect;
    private Long outStockUserId;
    private Date outStockTime;
    private Long auditUserId;
    private Date auditTime;
    private Long createUserId;
}
