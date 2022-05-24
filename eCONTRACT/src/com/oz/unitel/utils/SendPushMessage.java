package com.oz.unitel.utils;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;

public class SendPushMessage {

	public static void push(String deviceToken, String pushMsg, String certPath) throws Exception{
		
		PayLoad payLoad = new PayLoad();
		payLoad.addAlert(pushMsg); 
		payLoad.addBadge(1);
		payLoad.addSound("default");                  
		  
		PushNotificationManager pushManager = PushNotificationManager.getInstance();
				
		pushManager.addDevice(deviceToken, deviceToken);//iDevices is just alias name
					  
		//Connect to APNs
		String host = "gateway.sandbox.push.apple.com";
		int port = 2195;
		
		String certificatePath = certPath + "/ozhvacDevAPNSCert.p12"; 		
		String certificatePassword = "ozcloud";                                           
		pushManager.initializeConnection(host, port, certificatePath, certificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
		 
		//Send Push
		Device customer = pushManager.getDevice(deviceToken);
		pushManager.sendNotification(customer, payLoad);

		pushManager.stopConnection();
		pushManager.removeDevice(deviceToken);
		
	}	
}
