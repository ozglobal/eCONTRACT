package com.oz.unitel.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oz.unitel.utils.CustomGenericException;

@Controller
public class DownloadController {
	
	@Resource(name="unitelWs")
	private Properties unitelWs_prop;
	
	final static Logger logger = Logger.getLogger(DownloadController.class);
	
	/**
	* Download controller using FTP client 
	* @param contract_id, pdf_filename
	* @return JMessage
	* @throws Exception
	*/
	@RequestMapping(method=RequestMethod.POST, value="/download-file/ftp")
	public @ResponseBody HashMap<String, String> downloadFileFTP(@RequestParam("contractid") String contract_id, @RequestParam("pdffilename") String pdf_filename) throws FileUploadException, IOException {		
		String filePath = System.getProperty("econtract.root") + "resources/upload/";

		//Download file to FTP server 
		FTPClient ftpClient = new FTPClient();
		try {
	    	
	    	String ftp_host = unitelWs_prop.getProperty("ftp.host");
	    	String ftp_username = unitelWs_prop.getProperty("ftp.username");
	    	String ftp_password = unitelWs_prop.getProperty("ftp.password");
	    	int ftp_port = Integer.parseInt(unitelWs_prop.getProperty("ftp.port"));
	    	
	    	ftpClient.connect(ftp_host, ftp_port);
	    	ftpClient.login(ftp_username, ftp_password);
	    	
	    	String remoteFile = "/" + contract_id + "/" + pdf_filename;

	    	OutputStream os = new BufferedOutputStream(new FileOutputStream(filePath + File.separator + pdf_filename));
	    	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
	    	
	    	InputStream is = ftpClient.retrieveFileStream(remoteFile);

	    	byte[] bytesArray = new byte[4096];
	    	
            int bytesRead = -1;
            while ((bytesRead = is.read(bytesArray)) != -1) {
            	os.write(bytesArray, 0, bytesRead);
            }
			
            os.close();
            is.close();
            
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomGenericException("Fail");
		} finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	    
		Map<String, String> downloadReturn = new HashMap<String, String>();
		downloadReturn.put("pdf_filename", pdf_filename);
		
	    return (HashMap<String, String>) downloadReturn;// Download Success
	         
	}
	
	/**
	* Download controller using FTP client 
	* @param contract_id, pdf_filename
	* @return JMessage
	* @throws Exception
	*/
	@RequestMapping(method=RequestMethod.POST, value="/download-file/ftp/w")
	public void downloadFileW(@RequestParam("prodNo") String prodNo, @RequestParam("pdffilename") String pdf_filename, HttpServletResponse response) throws FileUploadException, IOException {		

		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "inline; filename=" + pdf_filename );
		
		//Download file to FTP server 
		FTPClient ftpClient = new FTPClient();
		try {
	    	
	    	String ftp_host = unitelWs_prop.getProperty("ftp.host");
	    	String ftp_username = unitelWs_prop.getProperty("ftp.username");
	    	String ftp_password = unitelWs_prop.getProperty("ftp.password");
	    	int ftp_port = Integer.parseInt(unitelWs_prop.getProperty("ftp.port"));
	    	
	    	ftpClient.connect(ftp_host, ftp_port);
	    	boolean success = ftpClient.login(ftp_username, ftp_password);
	    	logger.debug("FTP Login :: " + success);
	    	
	    	String remoteFile = "/" + prodNo + "/" + pdf_filename;	    	
	    	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				ftpClient.disconnect();
				logger.debug("Exception in connecting to FTP Server");
	        } else {	        	
	        	OutputStream os = response.getOutputStream();
	        	InputStream is = ftpClient.retrieveFileStream(remoteFile);
	            byte[] bytesArray = new byte[4096];
	            int bytesRead = -1;
	            while ((bytesRead = is.read(bytesArray)) != -1) {
	                os.write(bytesArray, 0, bytesRead);
	            }
	 
	            success = ftpClient.completePendingCommand();
	            if (success) {
	            	logger.debug("File has been downloaded successfully!");
	            }
	            
	        	response.flushBuffer();
	            
	            os.close();
	            is.close();
	        }	    	
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomGenericException("Fail");
		} finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	        
	}
}
