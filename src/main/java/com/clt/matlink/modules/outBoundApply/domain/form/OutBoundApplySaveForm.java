package com.clt.matlink.modules.outBoundApply.domain.form;

import com.clt.matlink.modules.outBoundApply.domain.entity.OutBoundApply;
import lombok.Data;

@Data
public class OutBoundApplySaveForm  extends OutBoundApply {
    private Long deptId;
}
