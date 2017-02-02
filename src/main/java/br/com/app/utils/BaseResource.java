package br.com.app.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import org.springframework.http.HttpStatus;

public class BaseResource {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<MethodArgumentNotValidResponse> validationError(MethodArgumentNotValidException ex) {
	    
		BindingResult result = ex.getBindingResult();
	    
		List<FieldError> fieldErrors = result.getFieldErrors();
		
		return this.getMethodArgumentNotValidResponse(fieldErrors);
	}
	
	/**
	 * build the response for MethodArgumentNotValidException
	 * @param fieldErrors
	 * @return
	 */
	public ResponseEntity<MethodArgumentNotValidResponse> getMethodArgumentNotValidResponse(List<FieldError> fieldErrors){
		
		MethodArgumentNotValidResponse response = new MethodArgumentNotValidResponse("INVALID_PARAMS",fieldErrors);

	    return new ResponseEntity<MethodArgumentNotValidResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Object used to response MethodArgumentNotValidException errors
	 * @author rafae	 *
	 */
	public class MethodArgumentNotValidResponse{
		public String errorCode;
		public List<FieldError> fieldErrors;
		
		public MethodArgumentNotValidResponse(String errorCode, List<FieldError> fieldErrors){
			this.errorCode=errorCode;
			this.fieldErrors=fieldErrors;
		}
	}
	
	/**
	 * build the success response boby
	 * @param message
	 * @return
	 */
	public 	ResponseEntity<SuccessResponse> getSuccessResponse(String message){
		return new ResponseEntity<SuccessResponse>(new SuccessResponse(message), HttpStatus.OK);
	}
	
	/**
	 * success response body 
	 * @author rafae
	 */
	public class SuccessResponse{
		public String message;
		
		public SuccessResponse(String message){
			this.message=message;
		}
	}

}
