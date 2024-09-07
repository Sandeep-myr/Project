package com.rajutech.project.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProjectMaterialTransferRestrictionResponse {

    private Long id;
    private String code;
    private String name;
    private Boolean item;
    private String unitOfMeasureName;
    private Long projectMaterailClassId;
    private Boolean isMaterialInternalTrx;
    private Boolean isMaterialExternalTrx;
    private List<ProjectMaterialTransferRestrictionResponse> childEntities = new ArrayList<>();
    
}
