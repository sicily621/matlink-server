package com.clt.matlink.modules.outBoundApply.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.outBoundApply.domain.entity.OutBoundApply;
import com.clt.matlink.modules.outBoundApply.domain.form.OutBoundApplyForm;
import com.clt.matlink.modules.outBoundApply.domain.vo.OutBoundApplyVo;

import java.util.List;

public interface OutBoundApplyService {
    OutBoundApply save(OutBoundApply outBoundApply);

    OutBoundApply getById(Long id);

    List<OutBoundApply> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<OutBoundApply> list(OutBoundApplyForm outBoundApplyForm);

    PageInfo<OutBoundApplyVo> page(OutBoundApplyForm outBoundApplyForm, PageQuery pageQuery);

    List<OutBoundApply> batchSave(List<OutBoundApply> outBoundApplys);
}
