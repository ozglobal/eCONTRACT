
package com.oz.unitel.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sun.misc.BASE64Decoder;

import com.oz.unitel.dao.ContractDAO;
import com.oz.unitel.model.Contracts;
import com.oz.unitel.security.JUserDetails;
import com.oz.unitel.utils.CustomGenericException;
import com.oz.unitel.utils.SendMailUtil;
import com.oz.unitel.utils.StringConverterUtils;

@Controller
public class UploadController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource(name="unitelWs")
	private Properties unitelWs_prop;
	
	@Autowired
	private SendMailUtil mailSender;
	
	@Autowired
	private ContractDAO contractDAO;
	
	private static final String COMPLETED_STATUS = "C";
	
	/**
	* Upload controller to upload resources folder 
	* @param MultipartHttpServletRequest (file_stream_?, file_name_?, prod_no)
	* @return JMessage
	* @throws Exception
	*/
	@RequestMapping(method=RequestMethod.POST, value="/upload-file-w")
	public @ResponseBody HashMap<String, String> uploadFileW(MultipartHttpServletRequest request) {		
        
		String filePath = System.getProperty("econtract.root") + "resources/upload/";
		
		String _file_name = "";
		String prodNo = "";

		HashMap<String, String> files = new HashMap<>();
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart){
    	    
    	    String key, value;
    	    Enumeration<String> enumer = request.getParameterNames();
    	    while (enumer.hasMoreElements()) {
    	    	key = enumer.nextElement();
    	    	value = request.getParameter(key);
    	    	if(key.indexOf("file_stream_") >= 0){   
    	    		String fileNameKey = "file_name_" + key.substring(key.lastIndexOf("_") + 1);
    	    		
    	    		String fileName = request.getParameter(fileNameKey);
    	    		String ext = fileName.substring(fileName.lastIndexOf("."));
    	    		fileName = fileNameKey + ext;
    	    		
    	    		files.put(fileName, value);// Put all filestreams to map    	    		
    	    	} else if(key.equals("prod_no")){
    	    		prodNo = value;
    	    	} 
    	    	
    	    }
    	    
    	    java.util.Date currentDate = new java.util.Date();   
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
			
			_file_name = prodNo + "_" + formatter.format(currentDate);
			
			// Upload file to upload folder 
			try {
    	    	    		
    			// Save pdf and ozd stream to file storage
    			for(Map.Entry<String, String> entry : files.entrySet()){    				
    				byte[] decodedBytes = new BASE64Decoder().decodeBuffer(entry.getValue().substring(entry.getValue().indexOf(",")+1));
    				
    				// Change the file name to format: prodNo_currentdate.xxx
    				
    				String _ext = entry.getKey().substring(entry.getKey().lastIndexOf("."));
    				_file_name = _file_name + _ext;

    				FileOutputStream fout = new FileOutputStream(filePath + File.separator + _file_name);
        	        fout.write(decodedBytes, 0, decodedBytes.length);
        	        fout.flush();
        	        fout.close();
        	      
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    			//throw new CustomGenericException("Submitting Error: Failed to upload temporary ozd file to server");
    			throw new CustomGenericException("Илгээхэд алдаа гарлаа: Түр ашиглах ozd файлыг серверт илгээхэд алдаа гарлаа");
    		} 

        }  
                
        Map<String, String> uploadReturn = new HashMap<String, String>();
		uploadReturn.put("return_filename", _file_name);
		
	    return (HashMap<String, String>) uploadReturn;// Download Success     
	}
	
	/**
	* Upload controller using FTP client 
	* @param MultipartHttpServletRequest (file_stream_?, file_name_?, prod_no)
	* @return JMessage
	* @throws Exception
	*/
	@RequestMapping(method=RequestMethod.POST, value="/upload-file/ftp")
	public @ResponseBody HashMap<String, String> uploadFileFTP(MultipartHttpServletRequest request) throws FileUploadException, IOException {		
		JUserDetails authUser = (JUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		logger.debug("Init /upload-file/ftp services :: " + new DateTime());
		
		String filePath = System.getProperty("econtract.root") + "resources/upload/";
		
		String xmlInputData = "";
		String xmlFilePath = filePath + "xml_contractdata_temp.xml";
		
		String prodNo = "";
		String pdf_file_name ="";
				
		HashMap<String, String> files = new HashMap<>();
		
		logger.debug("Start >> Upload file to ftp server :: " + new DateTime());
		
		HashMap<String, String> uploadReturn = new HashMap<>();// return data
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart){
    	    
    	    String key, value;
    	    Enumeration<String> enumer = request.getParameterNames();
    	    while (enumer.hasMoreElements()) {
    	    	key = enumer.nextElement();
    	    	value = request.getParameter(key);
    	    	if(key.indexOf("file_stream_") >= 0){   
    	    		String fileNameKey = "file_name_" + key.substring(key.lastIndexOf("_") + 1);
    	    		
    	    		String fileName = request.getParameter(fileNameKey);
    	    		String ext = fileName.substring(fileName.lastIndexOf("."));
    	    		fileName = fileNameKey + ext;
    	    		
    	    		files.put(fileName, value);// Put all filestreams to map    	    		
    	    	} else if(key.equals("prod_no")){
    	    		prodNo = value.trim();
    	    	} else if(key.equals("contract_xml_data")){
    	    		xmlInputData = value;
    	    	}
    	    	
    	    }
    	    
    	    logger.debug("Input XML data before upload ftp :: " + xmlInputData);
    	    
    	    java.util.Date currentDate = new java.util.Date();   
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
			
			//Upload file to FTP server 
			FTPClient ftpClient = new FTPClient();
			String _file_name = "";
			try {
    	    	
    	    	String ftp_host = unitelWs_prop.getProperty("ftp.host");
    	    	String ftp_username = unitelWs_prop.getProperty("ftp.username");
    	    	String ftp_password = unitelWs_prop.getProperty("ftp.password");
    	    	int ftp_port = Integer.parseInt(unitelWs_prop.getProperty("ftp.port"));
    	    	
    	    	ftpClient.connect(ftp_host, ftp_port);
    	    	ftpClient.login(ftp_username, ftp_password);
    	    	   	    	
    	    	// Create prodNo directory in FTP server
    	    	boolean dirExists = ftpClient.changeWorkingDirectory(prodNo);
    	    	if(!dirExists) {
    	    		if(ftpClient.makeDirectory(prodNo)) {
    	    			if(!ftpClient.changeWorkingDirectory(prodNo)){
    	    				throw new IOException("Unable to change into newly created remote directory '" + prodNo + "'.  error='" + ftpClient.getReplyString()+"'");
    	    			}
    	    		}else {
    	    			throw new IOException("Unable to create remote directory '" + prodNo + "'.  error='" + ftpClient.getReplyString()+"'");
    	    		}
    	    		
    	    	}
    	    	
    			for(Map.Entry<String, String> entry : files.entrySet()){
    				
    				byte[] decodedBytes = new BASE64Decoder().decodeBuffer(entry.getValue().substring(entry.getValue().indexOf(",")+1));
    				
    				// Change the file name to format: prodNo_currentdate.xxx
    				_file_name = prodNo + "_" + formatter.format(currentDate);
    				String _ext = entry.getKey().substring(entry.getKey().lastIndexOf("."));
    				_file_name = _file_name + _ext;
    				    			   				
    				// Store pdf and ozd stream into eCONTRACT upload directory
    				FileOutputStream fout = new FileOutputStream(filePath + File.separator + _file_name);
        	        fout.write(decodedBytes, 0, decodedBytes.length);
        	        fout.flush();
        	        fout.close();
    				
        	        // Store pdf and ozd stream into ftp server
    				InputStream is = new ByteArrayInputStream(decodedBytes);
  
    				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    				ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
    				ftpClient.enterLocalPassiveMode();
    				
    				ftpClient.storeFile(_file_name, is);
    				is.close();
    				
    			} 	
    			
    			logger.debug("End >> Upload file to ftp server :: " + new DateTime());
			} catch (Exception e) {
				logger.debug("Fail to upload to Ftp server");
    			e.printStackTrace();
    			//throw new CustomGenericException("Submitting Error: Failed to upload file to FTP server");
    			throw new CustomGenericException("Илгээхэд алдаа гарлаа: FTP серверт илгээж чадсангүй");
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
    		
			Contracts contract = new Contracts();
    		try {
    			
    			logger.debug("Start >> Insert inputformdata to database :: " + new DateTime());
    			
    			StringConverterUtils.stringToDom(xmlInputData, xmlFilePath);
    			
    			File _xml_file = new File(xmlFilePath);
            	JAXBContext jaxbContext = JAXBContext.newInstance(Contracts.class);
            	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            	
            	contract = (Contracts) jaxbUnmarshaller.unmarshal(_xml_file);
    			contract.setContract_status(COMPLETED_STATUS);
    			contract.setOzd_File_name(_file_name);
    			
    			pdf_file_name = _file_name.replace(".ozd", ".pdf");
    			contract.setPdf_File_name(pdf_file_name);

    			Contracts prevContract = contractDAO.getLatestContract(contract.getProduct_number());
            	if(prevContract != null){
            		contract.setCreated_id(prevContract.getCreated_id());
            	}
    			Date today = new Date(); 
            	contract.setUpdated_date(new Timestamp(today.getTime()));
            	contract.setUpdated_id(authUser.getUsername());
            	
            	int id = contractDAO.save(contract); // Save updated contract
            	
            	logger.debug("End >> Insert inputformdata to database :: id = " + id);
            	logger.debug("End >> Insert inputformdata to database :: " + new DateTime());

            	uploadReturn.put("id", Integer.toString(id));
            	uploadReturn.put("contract_type", contract.getContract_type());
    		}catch (Exception e) {
    			//throw new CustomGenericException("Submitting Error: Failed to update to database");
				throw new CustomGenericException("Илгээхэд алдаа гарлаа: Баазад өөрчлөлтийг оруулж чадсангүй");
			}		
        }  else {
        	//throw new CustomGenericException("Submitting Error: File upload request has error");
        	throw new CustomGenericException("Илгээхэд алдаа гарлаа: Файл илгээх хүсэлт алдаатай байна");
        }

        logger.debug("Complete /upload-file/ftp services :: " + new DateTime());
        
        return uploadReturn;// Upload Success    
	}
	
	@ExceptionHandler(CustomGenericException.class)
	public ResponseEntity<String> handleCustomException(CustomGenericException ex) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
        return new ResponseEntity<String>(ex.getErrMsg(), headers, HttpStatus.INTERNAL_SERVER_ERROR); 
	}
}
