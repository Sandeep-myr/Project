/**
* @author  Prakash Kumar Sahoo
* @version 2.0
* @since   08-16-2024
*/
package com.rajutech.project.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Response {
	private int statusCode;
	private String message;
	private Object responsePayload;
}
