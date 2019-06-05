package com.alan.test;

import java.io.IOException;


import com.oracle.e1.aisclient.CapabilityException;
import com.oracle.e1.aisclient.FSREvent;
import com.oracle.e1.aisclient.FormRequest;
import com.oracle.e1.aisclient.JDERestServiceException;
import com.oracle.e1.aisclient.JDERestServiceProvider;
import com.oracle.e1.aisclient.LoginEnvironment;

public class LoginJDE {
	public static void main(String[] args) {

	//login with minimum required information
	final String AIS_SERVER = "http://172.18.206.142:8081";
	final String USER_NAME = "JDE";
	final String PASSWORD = "JDEadmin";
	final String DEVICE = "Java";
	LoginEnvironment  loginEnv = null;
	try {
		 loginEnv = new LoginEnvironment(AIS_SERVER, USER_NAME, PASSWORD, DEVICE);
	System.out.print(loginEnv.getAisServerURL());
//	//login overrides default environment and role
//	final String ENVIRONMENT = "JPY920";
//	final String ROLE = "*ALL";
//	LoginEnvironment  loginEnv2 = new LoginEnvironment(AIS_SERVER, USER_NAME, PASSWORD, ENVIRONMENT, ROLE, DEVICE);
//	 
//	//login with required capabilities
//	//A CapabilityException will be thrown if AIS doesn't have those in the list
//	final String REQ_CAPABILITIES = "grid, processingOption";
//	LoginEnvironment  loginEnv3 = new LoginEnvironment(AIS_SERVER, USER_NAME, PASSWORD, DEVICE, REQ_CAPABILITIES);
//	 
//	//login with token
//	String PS_TOKEN = "a ps token string";
//	LoginEnvironment  loginEnv4 = new LoginEnvironment(AIS_SERVER, USER_NAME, null, null, null, DEVICE, null, null, PS_TOKEN);
	
	FormRequest formRequest = new FormRequest(loginEnv);
	formRequest.setFormName("P01012_W01012B");
	formRequest.setFormServiceAction("R");
	formRequest.setMaxPageSize("30"); //only get 30 records
	formRequest.setReturnControlIDs("54|1[19,20]");
	//formRequest.setOutputType(loginEnv, "GRID_DATA");

	FSREvent fsrEvent = new FSREvent();
	 
	 
	fsrEvent.setFieldValue("54", "E"); //customers
	//include >= operator in QBE
	fsrEvent.setQBEValue("1[19]", ">=" + "6001");
	fsrEvent.checkBoxChecked("62"); //show address
	fsrEvent.checkBoxChecked("63"); //show phone
	fsrEvent.doControlAction("15"); //find
	formRequest.addFSREvent(fsrEvent); //add the events to the request
	
	String response = JDERestServiceProvider.jdeRestServiceCall(loginEnv, formRequest, JDERestServiceProvider.POST_METHOD, JDERestServiceProvider.FORM_SERVICE_URI);
	System.out.print(response);
	        //de-serialize the JSON string into the form parent object
	//p01012form = loginEnv.getObjectMapper().readValue(response, P01012_W01012B_FormParent.class);
	} catch (CapabilityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JDERestServiceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	}
	
	

	
}
