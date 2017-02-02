package br.com.app.utils;

@SuppressWarnings("serial")
public class ServiceLayerException extends Exception{
	
	public static final String ERROR_SERVICE_LAYER = "ERROR_SERVICE_LAYER_";
	
	public ServiceLayerException(String msg){
		super(msg);
	}
}
