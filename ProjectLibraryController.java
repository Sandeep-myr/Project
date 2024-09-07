package com.rajutech.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rajutech.project.common.Constants;
import com.rajutech.project.common.URLConstants;
import com.rajutech.project.dao.model.LoggedInUserDetails;
import com.rajutech.project.exception.POTException;
import com.rajutech.project.request.MaterialRestrictionRequest;
import com.rajutech.project.request.ProjectEmployeeClassRequest;
import com.rajutech.project.request.ProjectPlantClassRequest;
import com.rajutech.project.request.WarehouseStockyardRequest;
import com.rajutech.project.response.ProjWarehouseStockyardResponse;
import com.rajutech.project.response.ProjectEmployeeClassificationResponse;
import com.rajutech.project.response.ProjectMaterialTransferRestrictionResponse;
import com.rajutech.project.response.ProjectPlantClassificationResponse;
import com.rajutech.project.response.Response;
import com.rajutech.project.service.ProjectEmployeeClassficationService;
import com.rajutech.project.service.ProjectMaterialTransferRestrictionService;
import com.rajutech.project.service.ProjectPlantClassficationService;
import com.rajutech.project.service.WarehouseStockyardService;
import com.rajutech.project.util.AppUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Project Library Module")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URLConstants.ProjectLibrary.API_BASE)
@AllArgsConstructor
public class ProjectLibraryController  extends BaseController{
	
	
	private ProjectEmployeeClassficationService _projectEmployeeClassficationService;
	
	private  ProjectMaterialTransferRestrictionService _projectMaterialTransferRestrictionService;

	private WarehouseStockyardService warehouseStockyardService;

	private ProjectPlantClassficationService projectPlantClassficationService;
	
	//================================================================================================
	
	@Operation(summary = "Get All Project Employee Classifications", 
	           description = "This service is used for fetching all employee classifications for a specific project based on their status.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Project Employee Classifications fetched successfully", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "204", 
	                 description = "No Project Employee Classifications found", 
	                 content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping(URLConstants.ProjectLibrary.GET_PROJ_EMP_CLASSIFICATION)
	public ResponseEntity<Response> getProjEmpClassification(
	        @RequestParam(name = "projectId", required = true) Long projectId) throws POTException {
	    Response response = null;
	    LoggedInUserDetails loggedInUserDetails = getLoggedInUserDetails();
	    List<ProjectEmployeeClassificationResponse> employeeClassifications = null;
	    
	    employeeClassifications = _projectEmployeeClassficationService.getAllProjEmpClassification(loggedInUserDetails, projectId);
	    
	    if (AppUtil.isNotNull(employeeClassifications)) {
	        response = new Response(Constants.OK, "Project Employee Classifications fetched successfully.", employeeClassifications);
	    } else {
	        response = new Response(Constants.NO_CONTENT, "No Project Employee Classifications found.", null);
	    }
	    
	    return getOKResponseEntity(response);
	}

	
	//================================================================================================
	
	@Operation(summary = "Save Project Employee Classification", 
	           description = "This service is used to save multiple project employee classifications at once.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Project employee classifications saved successfully", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "400", 
	                 description = "Invalid input provided", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "10025", 
	                 description = "Data saving error", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "500", 
	                 description = "Internal Server Error", 
	                 content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping(URLConstants.ProjectLibrary.SAVE_PROJ_EMP_CLASSIFICATION)
	public ResponseEntity<Response> saveProjectEmployeeClassification(@Valid @RequestBody ProjectEmployeeClassRequest classificationRequests) throws POTException {
	    Response response = null;
	    LoggedInUserDetails loggedInUserDetails = getLoggedInUserDetails();
	    if (AppUtil.isNotNull(classificationRequests.getClassificationList())) {
	        Map<String, Object> isSaved = _projectEmployeeClassficationService.saveAllProjectEmployeeClassifications(loggedInUserDetails, classificationRequests);
	        Boolean status = (Boolean) isSaved.get("isSuccess");
	        String message = (String) isSaved.get("message");
	        
	        if (status) {
	            response = new Response(Constants.OK, "Project employee classifications saved successfully.", message);
	        } else {
	            response = new Response(Constants.DATA_SAVING_ERROR, "Failed to save project employee classifications.", message);
	        }
	    } else {
	        response = new Response(Constants.INVALID_INPUT, "Invalid input provided. Please provide valid project employee classification requests.", null);
	    }

	    return getOKResponseEntity(response);
	}

	
	//================================================================================================
	
	@Operation(summary = "Get Project Material Transfer Restrictions", 
	           description = "This service fetches all material transfer restrictions for a specific project.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Project Material Transfer Restrictions fetched successfully", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "204", 
	                 description = "No Project Material Transfer Restrictions found", 
	                 content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping(URLConstants.ProjectLibrary.GET_PROJ_MAT_TRANSFER_RESTRICTION)
	public ResponseEntity<Response> getProjMatTransferRestrictions(
	        @RequestParam(name = "projectId", required = true) Long projectId) throws POTException {
	    
	    Response response = null;
	    LoggedInUserDetails loggedInUserDetails = getLoggedInUserDetails();
	    List<ProjectMaterialTransferRestrictionResponse> materialTransferRestrictions = null;
	    
	    materialTransferRestrictions = _projectMaterialTransferRestrictionService.getAllProjMatTransferRestrictions(loggedInUserDetails, projectId);
	    
	    if (AppUtil.isNotNull(materialTransferRestrictions)) {
	        response = new Response(Constants.OK, "Project Material Transfer Restrictions fetched successfully.", materialTransferRestrictions);
	    } else {
	        response = new Response(Constants.NO_CONTENT, "No Project Material Transfer Restrictions found.", null);
	    }
	    
	    return getOKResponseEntity(response);
	}

	//================================================================================================================
	
	@Operation(summary = "Save Project Material Transfer Restrictions", 
	           description = "This service is used to save multiple project material transfer restrictions at once.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Project material transfer restrictions saved successfully", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "400", 
	                 description = "Invalid input provided", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "10025", 
	                 description = "Data saving error", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "500", 
	                 description = "Internal Server Error", 
	                 content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping(URLConstants.ProjectLibrary.SAVE_PROJ_MAT_TRANSFER_RESTRICTION)
	public ResponseEntity<Response> saveProjectMaterialTransferRestrictions(@Valid @RequestBody MaterialRestrictionRequest transferRequests) throws POTException {
	    Response response;
	    LoggedInUserDetails loggedInUserDetails = getLoggedInUserDetails();

	    if (AppUtil.isNotNull(transferRequests.getProjMaterialRestrictionRequests())) {
	        Map<String, Object> result = _projectMaterialTransferRestrictionService.saveAllProjectMaterialTransferRestrictions(loggedInUserDetails, transferRequests);
	        Boolean status = (Boolean) result.get("isSuccess");
	        String message = (String) result.get("message");
	        if (status) {
	            response = new Response(Constants.OK, "Project material transfer restrictions saved successfully.", message);
	        } else {
	            response = new Response(Constants.DATA_SAVING_ERROR, "Failed to save project material transfer restrictions.", message);
	        }
	    } else {
	        response = new Response(Constants.INVALID_INPUT, "Invalid input provided. Please provide valid project material transfer restriction requests.", null);
	    }

	    return getOKResponseEntity(response);
	}

	//================================================================================================
	
	@Operation(summary = "Get Project Warehouse and Stockyard List", 
	           description = "This service is used for fetching all project warehouses and stockyards based on their status.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Project Warehouse and Stockyard List fetched successfully", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "204", 
	                 description = "No Project Warehouse and Stockyard found", 
	                 content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping(URLConstants.ProjectLibrary.GET_PROJ_WARE_AND_STOCK_YARD_LIST)
	public ResponseEntity<Response> getProjWarehouseAndStockyardList(@RequestParam(name = "projectId" , required = true) Long projectId,  @RequestParam(name = "status", required = true) Integer status) throws POTException {
	    Response response = null;
	    LoggedInUserDetails loggedInUserDetails = getLoggedInUserDetails();
	    List<ProjWarehouseStockyardResponse> warehouseStockyardResponse = warehouseStockyardService.getProjWarehouseAndStockyardList(loggedInUserDetails, status , projectId);
	    
	    if (AppUtil.isNotNull(warehouseStockyardResponse)) {
	        response = new Response(Constants.OK, "Project Warehouse and Stockyard List fetched successfully.", warehouseStockyardResponse);
	    } else {
	        response = new Response(Constants.NO_CONTENT, "No Project Warehouse and Stockyard found.", null);
	    }
	    return getOKResponseEntity(response);
	}

	
	//=============================================================================================
	
	@Operation(summary = "Save Project Warehouse and Stockyard List", 
	           description = "This service is used for saving project warehouses and stockyards.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Project Warehouse and Stockyard List saved successfully.", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "422", 
	                 description = "Invalid input: Empty Data List", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "10025", 
	                 description = "Data Not Saved", 
	                 content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping(URLConstants.ProjectLibrary.SAVE_PROJ_WARE_HOUSE_AND_STOCK_YARD_LIST)
	public ResponseEntity<Response> saveProjWarehouseAndStockyardList(@Valid @RequestBody WarehouseStockyardRequest warehouseStockyardRequest) throws POTException {
	    Response response = null;
	    
	    if (warehouseStockyardRequest!=null) {
	        LoggedInUserDetails loggedInUserDetails = getLoggedInUserDetails();
	        Map<String, Object> warehouseStockyardResponse = warehouseStockyardService.saveProjWarehouseAndStockyardList(warehouseStockyardRequest, loggedInUserDetails);
	        Boolean isSaved = (Boolean) warehouseStockyardResponse.get("isSuccess");
	        String message = (String) warehouseStockyardResponse.get("message");
	        
	        if (isSaved) {
	            response = new Response(Constants.OK, "Project Warehouse and Stockyard List saved successfully.", message);
	        } else {
	            response = new Response(Constants.DATA_SAVING_ERROR, "Data Not Saved", message);
	        }
	    } else {
	        response = new Response(Constants.INVALID_INPUT, "Empty Data List", null);
	    }
	    
	    return getOKResponseEntity(response);
	}

	//=====================================================================================
	
	@Operation(summary = "Deactivate or Activate Project Warehouse and Stockyard", 
	           description = "This service is used for deactivating or activating a project warehouse or stockyard based on its ID and status.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Request Status Successfully Updated", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "10025", 
	                 description = "Data Not Saved", 
	                 content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping(URLConstants.ProjectLibrary.DEACTIVATE_WARE_HOUSE_AND_STOCK_YARD)
	public ResponseEntity<Response> deactivateWarehouseAndStockyard(@RequestParam(name = "ids", required = true) List<Long> id, 
	                                                                @RequestParam(name = "status", required = true) Integer status) throws POTException {
	    Response response = null;
	    Boolean isDeactivated = warehouseStockyardService.deactivateWarehouseAndStockyard(id, status);
	    
	    if (isDeactivated) {
	        response = new Response(Constants.OK, "Request Status Successfully Updated", isDeactivated);
	    } else {
	        response = new Response(Constants.DATA_SAVING_ERROR, "Request Failed", null);
	    }
	    
	    return getOKResponseEntity(response);
	}

	//===========================================================================================
	
	@Operation(summary = "Get All Project Plant Classifications", 
	           description = "This service is used for fetching all plant classifications for a specific project based on their status.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Project Plant Classifications fetched successfully", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "204", 
	                 description = "No Project Plant Classifications found", 
	                 content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping(URLConstants.ProjectLibrary.GET_PROJ_PLANT_CLASSIFICATION)
	public ResponseEntity<Response> getProjPlantClassification(
	        @RequestParam(name = "projectId", required = true) Long projectId) throws POTException {
	    
	    Response response = null;
	    LoggedInUserDetails loggedInUserDetails = getLoggedInUserDetails();
	    List<ProjectPlantClassificationResponse> plantClassifications = null;
	    plantClassifications = projectPlantClassficationService.getAllProjPlantClassification(loggedInUserDetails, projectId);
	    if (AppUtil.isNotNull(plantClassifications)) {
	        response = new Response(Constants.OK, "Project Plant Classifications fetched successfully.", plantClassifications);
	    } else {
	        response = new Response(Constants.NO_CONTENT, "No Project Plant Classifications found.", null);
	    }
	    return getOKResponseEntity(response);
	}

	//===================================================================================================
	
	@Operation(summary = "Save Project Plant Classification", 
	           description = "This service is used to save multiple project plant classifications at once.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Project plant classifications saved successfully", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "400", 
	                 description = "Invalid input provided", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "10025", 
	                 description = "Data saving error", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "500", 
	                 description = "Internal Server Error", 
	                 content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping(URLConstants.ProjectLibrary.SAVE_PROJ_PLANT_CLASSIFICATION)
	public ResponseEntity<Response> saveProjectPlantClassification(@Valid @RequestBody ProjectPlantClassRequest classificationRequests) throws POTException {
	    Response response = null;
	    LoggedInUserDetails loggedInUserDetails = getLoggedInUserDetails();
	    if (AppUtil.isNotNull(classificationRequests.getClassificationList())) {
	        Map<String, Object> isSaved = projectPlantClassficationService.saveAllProjectPlantClassifications(loggedInUserDetails, classificationRequests);
	        Boolean status = (Boolean) isSaved.get("isSuccess");
	        String message = (String) isSaved.get("message");
	        
	        if (status) {
	            response = new Response(Constants.OK, "Project plant classifications saved successfully.", message);
	        } else {
	            response = new Response(Constants.DATA_SAVING_ERROR, "Failed to save project plant classifications.", message);
	        }
	    } else {
	        response = new Response(Constants.INVALID_INPUT, "Invalid input provided. Please provide valid project plant classification requests.", null);
	    }

	    return getOKResponseEntity(response);
	}

	
}
