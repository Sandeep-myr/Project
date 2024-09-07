package com.rajutech.project.request;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProjMaterialRestrictionRequest {
	    private Long materialClassid;
	    private Boolean isItem;
	    private Long projectMaterailClassId;
	    private Boolean isMaterialInternalTrx;
	    private Boolean isMaterialExternalTrx;
	    private List<ProjMaterialRestrictionRequest> childEntities = new ArrayList<>();
}
