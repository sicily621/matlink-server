package com.clt.matlink.modules.task.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * 物料管理-盘点明细表实体类
 */
@Data
@Schema(description = "物料管理-盘点明细表")
public class TaskDetailExeclVO  {

    @ExcelProperty(value = "id")
    @Schema(description = "id")
    private Long id;
    @ExcelProperty(value = "任务ID")
    @Schema(description = "任务ID")
    private Long taskId;
    @ExcelProperty(value = "任务名称")
    @Schema(description = "任务名称")
    private String name;
    @ExcelProperty(value = "物料ID")
    @Schema(description = "物料ID")
    private Long materialId;
    @ExcelProperty(value = "物料名称")
    @Schema(description = "物料名称")
    private String materialName;
    @ExcelProperty(value = "型号")
    @Schema(description = "型号")
    private String modelNo;
    @ExcelProperty(value = "品牌")
    @Schema(description = "品牌")
    private String brand;
    @ExcelProperty(value = "规格")
    @Schema(description = "规格")
    private String specification;
    @ExcelProperty(value = "最小库存量")
    @Schema(description = "最小库存量")
    private Integer minCountLimit;
    @ExcelProperty(value = "最大库存量")
    @Schema(description = "最大库存量")
    private Integer maxCountLimit;
    @ExcelProperty(value = "安全库存量")
    @Schema(description = "安全库存量")
    private Integer safeCountLimit;
    @ExcelProperty(value = "实际数量")
    @Schema(description = "实际数量")
    private BigDecimal realCount;
    @ExcelProperty(value = "账面库存数量")
    @Schema(description = "账面库存数量")
    private BigDecimal bookCount;
    @ExcelProperty(value = "差异数量")
    @Schema(description = "差异数量")
    private BigDecimal diffCount;
    @ExcelProperty(value = "状态")
    @Schema(description = "状态：0 正常 1盘盈 2盘亏")
    private Integer status;
}
