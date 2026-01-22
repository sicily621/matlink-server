package com.clt.matlink.modules.outBoundApply.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("clt_material_outbound_apply")
public class OutBoundApplyDetail  extends BaseEntity {
    private Long id;
    private Long applyId;
    private Long materialId;
    private BigDecimal applyCount;
    private BigDecimal actualCount;
}
