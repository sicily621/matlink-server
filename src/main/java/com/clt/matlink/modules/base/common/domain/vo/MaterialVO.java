package com.clt.matlink.modules.base.common.domain.vo;

import com.clt.matlink.modules.base.common.domain.entity.Material;
import lombok.Data;

import java.util.List;


@Data
public class MaterialVO extends Material {

        private List<String> imageUrls;
}
