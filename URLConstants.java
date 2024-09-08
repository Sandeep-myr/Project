/**
* @author  Prakash Kumar Sahoo, Sandeep Sahu
* @version 2.0
* @since   08-16-2024
*/
package com.rajutech.project.common;

public class URLConstants {

	// ----------------------------------------------------------------

	public class Projects {
		public static final String API_BASE = "/api/project/settings";
		public static final String PGV_SAVE = "/save/pgv";
	}

	// ----------------------------------------------------------------

	public class ProjectLibrary {
		public static final String API_BASE = "/api/project/library";

		// Project Employee Classification APi's Start here --

		public static final String GET_PROJ_EMP_CLASSIFICATION = "/proj/emp/classification/get";
		public static final String SAVE_PROJ_EMP_CLASSIFICATION = "/proj/emp/classification/save";

		// Project Material Transfer Restriction APi's Start here --

		public static final String GET_PROJ_MAT_TRANSFER_RESTRICTION = "/proj/mat/transfer/restrictions/get";
		public static final String SAVE_PROJ_MAT_TRANSFER_RESTRICTION = "/proj/mat/transfer/restrictions/save";

		// Project Ware House And Stock Yard Restriction APi's Start here --

		public static final String GET_PROJ_WARE_AND_STOCK_YARD_LIST = "/proj/warehouse/list/get";
		public static final String SAVE_PROJ_WARE_HOUSE_AND_STOCK_YARD_LIST = "/proj/warehouse/stockyard/list/save";
		public static final String DEACTIVATE_WARE_HOUSE_AND_STOCK_YARD = "/warehouse/stockyard/deactivate";

		// Project Plant Classification APi's Start here --

		public static final String GET_PROJ_PLANT_CLASSIFICATION = "/proj/plant/classification/get";
		public static final String SAVE_PROJ_PLANT_CLASSIFICATION = "/proj/plant/classification/save";
		
		// Project Work Shift APi's Start here --'
		
		public static final String GET_PROJ_WORK_SHIFT_LIST = "/proj/workshift/list/get";
		public static final String SAVE_PROJ_WORK_SHIFT_LIST = "/proj/workshift/list/save";
		public static final String DEACTIVATE_PROJ_WORK_SHIFT_LIST = "/proj/workshift/list/deactivate";
		
		
		
		

	}

}
