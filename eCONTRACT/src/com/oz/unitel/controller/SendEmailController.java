package com.oz.unitel.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oz.unitel.dao.ContractDAO;
import com.oz.unitel.model.Contracts;
import com.oz.unitel.utils.SendMailUtil;

@Controller
public class SendEmailController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource(name="unitelWs")
	private Properties unitelWs_prop;
	
	@Autowired
	private ContractDAO contractDAO;
	
	@Autowired
	private SendMailUtil mailSender;
	
	/**
	* Send Email 
	* @param MultipartHttpServletRequest (file_stream_?, file_name_?, prod_no)
	* @return JMessage
	* @throws Exception
	*/
	@RequestMapping(method=RequestMethod.POST, value="/send-email/{id}")
	public void uploadFileFTP(@PathVariable int id) throws FileUploadException, IOException {		
				
		String filePath = System.getProperty("econtract.root") + "resources/upload/";
		logger.debug("Start >> Send Email :: id = " + id);
		Contracts contract = contractDAO.getContract(id);
		
		// Send email
		if (contract != null) {
			try {
				
				logger.debug("Start >> Send Email to customer :: " + new DateTime());
		
				Map<String, String> filesMap = new HashMap<String, String>();
				
				filesMap.put("prod_no", contract.getProduct_number());    			
				filesMap.put("pdf_file_name", contract.getPdf_File_name());// Change file type to pdf
				
				// TODO: Need to change subject and email address('customerEmail') after testing
				String emailsenderFrom = unitelWs_prop.getProperty("emailsender.from");
				String customerName = contract.getFirst_name() + " " + contract.getLast_name();
				
				boolean sendmail_return = mailSender.sendMailWithoutAuthentication(emailsenderFrom, "Unitel e-Contract", customerName, contract.getEmail(), null, null, filesMap);
				
				logger.debug("End >> Send Email to customer :: " + new DateTime());
				
				// Delete pdf file after successful to sent email
				if (sendmail_return) {
					try{
			    		File file = new File(filePath + File.separator + contract.getPdf_File_name());
			    		file.delete();        	    		
			    	   
			    	} catch(Exception e){   	    		
			    		e.printStackTrace();    	    		
			    	}
				}			
			} catch (Exception e) {
				//throw new CustomGenericException("There is some problem when sending email. Please download pdf from admin page and do it manually.");
				//throw new CustomGenericException("Имэйл илгээхэд алдаа гарлаа. Админ хуудаснаас pdf файлыг татан авч гараар илгээнэ үү.");
				logger.debug(e.getMessage());
			}
			
			// Remove all old ozd files
	        try {
				File dir = new File(filePath);
				final String tobedeleted_filename = contract.getProduct_number();
				final File[] tobedeletedFiles = dir.listFiles(new FilenameFilter() {
					
				    @Override
				    public boolean accept(final File dir,
				                           final String filename) {
				        return filename.matches(tobedeleted_filename + ".*\\.ozd");
				    }
				});
				for (final File file : tobedeletedFiles) {
	    			if(!file.getName().equals(contract.getOzd_File_name())) { // Delete all prodNo's ozd except latest one
	    				if (!file.delete()) {
	    			        logger.debug( "Can't remove :: " + file.getAbsolutePath());
	    			    }
	    			}
	    		}
	        } catch (Exception e) {
				e.printStackTrace();
				//throw new CustomGenericException("Submitting Error: Failed to delete old ozd files");
				//throw new CustomGenericException("Илгээхэд алдаа гарлаа: Хуучин ozd файлуудыг устгаж чадсангүй");
				logger.debug(e.getMessage());
			}
		} else {
			logger.debug("Failed to send email");			
			//throw new CustomGenericException("Илгээхэд алдаа гарлаа: Хуучин ozd файлуудыг устгаж чадсангүй");
		}
	}
}
