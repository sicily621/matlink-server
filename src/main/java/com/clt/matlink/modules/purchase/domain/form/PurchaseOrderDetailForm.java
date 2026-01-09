package com.clt.matlink.modules.purchase.domain.form;

import lombok.Data;

@Data
public class PurchaseOrderDetailForm {

    private Long orderId;
    private Long supplierId;
    private Long categoryId;
    private Long productId;
}
