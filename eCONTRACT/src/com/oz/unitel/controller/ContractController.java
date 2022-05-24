package com.oz.unitel.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import mn.unitel.websvc.OnlinecontractNewResponse;
import mn.unitel.websvc.OnlinecontractResponse;
import mn.unitel.websvc.ucbs.xsd.ContractRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.oz.unitel.dao.ContractDAO;
import com.oz.unitel.model.Contracts;
import com.oz.unitel.security.JUserDetails;
import com.oz.unitel.utils.CustomGenericException;
import com.oz.unitel.utils.OZExport;
import com.oz.unitel.utils.SendMailUtil;
import com.oz.unitel.utils.StringConverterUtils;
import com.oz.unitel.ws.client.WsClient;
import com.oz.unitel.ws.client.WsConfiguration;


@Controller
public class ContractController {
	
	@Resource(name="unitelWs")
	private Properties unitelWs_prop;
	
	@Autowired
	private SendMailUtil mailSender;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private ContractDAO contractDAO;
	
	private ApplicationContext context;
	
	private static final String M_APPLICATION_OZVIEWER = "m-application-ozviewer";
	private static final String W_APPLICATION_OZVIEWER = "w-application-ozviewer";
	private static final String COMPLETED_LIST_VIEW = "w-completed-list";
	
	private static final String TELLER = "ROLE_TELLER";
	private static final String REPORTER = "ROLE_REPORTER";
	private static final String MANAGER = "ROLE_MANAGER";
	//private static final String CONTRACT_SPECIALIST = "ROLE_CONTRACT";
	private static final String ADMINISTRATOR = "ROLE_ADMIN";
	
	private static final String COMPLETED_STATUS = "C";
	private static final String DECLINE_STATUS = "D";
	private static final String INITIALIZE_STATUS = "I";
	
	final static Logger logger = Logger.getLogger(ContractController.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/m-application-ozviewer")
	public ModelAndView mApplicationOzViewer(@RequestParam("reportname") String reportname) {		
		JUserDetails authUser = (JUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("login_user", authUser.getUsername());
		model.put("user_role", authUser.getAuthorities().get(0).toString());
		model.put("report_name", reportname);
		
		return new ModelAndView(M_APPLICATION_OZVIEWER, "model", model);		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/m-application-ozviewer/seach")
	public @ResponseBody HashMap<String, String> getCustomerReportListM(@RequestParam("prod_no") String prodNo, @RequestParam("contract_type") String contractType) {		
		JUserDetails authUser = (JUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Contracts contract = new Contracts(); 
		if (hasRole(TELLER)) {
			//contract = contractDAO.getLatestUncompletedContract(prodNo, COMPLETED_STATUS, authUser.getUsername()); 
			contract = contractDAO.getLatestUncompletedContractJ(prodNo, contractType, COMPLETED_STATUS, authUser.getUsername());
		} else {
			//contract = contractDAO.getLatestUncompletedContract(prodNo, COMPLETED_STATUS);
			contract = contractDAO.getLatestUncompletedContractJJ(prodNo, contractType);
		} 
		
		if (contract != null) {
			HashMap<String, String> contractInfo = new HashMap<String, String>();
			contractInfo.put("contract_id", contract.getContract_id());
			contractInfo.put("prod_no", contract.getProduct_number());
			contractInfo.put("ozd_file_name", contract.getOzd_File_name());
			
			return (HashMap<String, String>)contractInfo;
		} else {
			throw new CustomGenericException("Хайсан дугаарын гэрээ байхгүй эсвэл өөр ажилтны гэрээ байна.");
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/m-application-ozviewer/decline")
	public @ResponseBody HashMap<String, String> declineCustomerContract(@RequestParam("contract_id") String contractId) {
		JUserDetails authUser = (JUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Contracts contract = contractDAO.getLatestContractJ(contractId);
		contract.setContract_status(DECLINE_STATUS);
		
		Date today = new Date(); 
		contract.setUpdated_date(new Timestamp(today.getTime()));
		contract.setUpdated_id(authUser.getUsername());
		
		try {
			contractDAO.save(contract);
			
			HashMap<String, String> contractInfo = new HashMap<String, String>();
			contractInfo.put("message", "success");
			
			return contractInfo;
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomGenericException("Fail");
		}		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/w-application-ozviewer")
	public ModelAndView wApplicationOzViewer(@RequestParam("reportname") String reportname, @RequestParam("prod_no") String prod_no, HttpServletRequest request, HttpServletResponse response) {		
		JUserDetails authUser = (JUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String svrIP = request.getServerName();
		int p = request.getServerPort();
		String svrPort = Integer.toString(p);
		String webappHost = "http://" + svrIP + ":" + svrPort + servletContext.getContextPath();
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("login_user", authUser.getUsername());
		model.put("user_role", authUser.getAuthorities().get(0).toString());
		model.put("report_name", reportname);
		model.put("prod_no", prod_no);
		model.put("webapp_host", webappHost);
		
		return new ModelAndView(W_APPLICATION_OZVIEWER, "model", model);		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/w-application-ozviewer/seach")
	public @ResponseBody HashMap<String, String> getCustomerReportList(@RequestParam("prodNo") String prodNo, @RequestParam("contractType") String contractType) {
		logger.debug("pham >> prodNo :: " + prodNo);
		logger.debug("pham >> contractType :: " + contractType);
		
		context = new AnnotationConfigApplicationContext(WsConfiguration.class); 
		WsClient wsclient = context.getBean(WsClient.class); 
		// Get Unitel ws properties
		String wsAuth = unitelWs_prop.getProperty("ws.auth");
		int wsId = Integer.parseInt(unitelWs_prop.getProperty("ws.id"));
		String successCode = unitelWs_prop.getProperty("ws.successcode");
		
		if(!contractType.equals("")) {
			// Get contract information data from Unitel data-service
			OnlinecontractNewResponse response = wsclient.getContractDataNew(wsAuth, wsId, prodNo, contractType);		
			JAXBElement<ContractRequest> _return = response.getReturn();
			logger.debug("pham >> Contract dataset success code :: " + _return.getValue().getResultcode().getValue());
			
			if (_return.getValue().getResultcode().getValue().equals(successCode)) {
				logger.debug("pham >> contractId :: " + _return.getValue().getEntrNo().getValue());
				Contracts contract = contractDAO.getContract(prodNo, contractType, _return.getValue().getEntrNo().getValue()); 
				
				if(contract == null) {
					HashMap<String, String> contractInfo = new HashMap<String, String>();
					contractInfo.put("contract_id", _return.getValue().getEntrNo().getValue());
					contractInfo.put("prod_no", _return.getValue().getProdNo().getValue());
					contractInfo.put("contract_type", _return.getValue().getContractType().getValue());
					contractInfo.put("contract_json", _return.getValue().getRs().getValue());
					
					return contractInfo;
				} else {
					throw new CustomGenericException("Биллингээс гэрээний мэдээллийг татах боломжгүй байна.");
				}
			} else {
				throw new CustomGenericException("Биллингээс мэдээлэл татах боломжгүй байна.");
			}
		} else {//TODO: Need to remote it when new soap is ready
			logger.debug("pham >> old billing soap service :: ");
			// Get contract information data from Unitel data-service
			OnlinecontractResponse response = wsclient.getContractData(wsAuth, wsId, prodNo);		
			JAXBElement<ContractRequest> _return = response.getReturn();
			logger.debug("pham >> Contract dataset success code :: " + _return.getValue().getResultcode().getValue());
			
			if (_return.getValue().getResultcode().getValue().equals(successCode)) {
				logger.debug("pham >> contractId :: " + _return.getValue().getEntrNo().getValue());
				Contracts contract = contractDAO.getContractJ(prodNo, _return.getValue().getEntrNo().getValue()); 
				
				if(contract == null) {
					HashMap<String, String> contractInfo = new HashMap<String, String>();
					contractInfo.put("contract_id", _return.getValue().getEntrNo().getValue());
					contractInfo.put("prod_no", _return.getValue().getProdNo().getValue());
					contractInfo.put("contract_type", _return.getValue().getContractType().getValue());
					contractInfo.put("contract_json", _return.getValue().getRs().getValue());
					
					return contractInfo;
				} else {
					throw new CustomGenericException("Биллингээс гэрээний мэдээллийг татах боломжгүй байна.");
				}
			} else {
				throw new CustomGenericException("Биллингээс мэдээлэл татах боломжгүй байна.");
			}
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/w-application-ozviewer/submit")
	public @ResponseBody HashMap<String, String> wApplicationOzViewerSubmit(MultipartHttpServletRequest request, HttpServletResponse response) {		
		JUserDetails authUser = (JUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String filePath = System.getProperty("econtract.root") + "resources/upload/";
			
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    	String xmlInputData = "";
    	String ozd_filename = "";
    	String file_type = "";
    	String xmlFilePath = filePath + "xml_contractdata_temp.xml";
    	if(isMultipart){
    	    
    	    String key, value;
    	    Enumeration<String> enumer = request.getParameterNames();
    	    while (enumer.hasMoreElements()) {
    	    	key = enumer.nextElement();
    	    	value = request.getParameter(key);
    	    	if(key.equals("contract_xml_data")){
    	    		xmlInputData = value;
    	    	} else if (key.equals("ozd_filename")){
    	    		ozd_filename = value;
    	    	} else if (key.equals("file_type")){
    	    		file_type = value;
    	    	}      	    	
    	    }
        }
    	
    	Contracts contract = new Contracts();
    	try {
    		StringConverterUtils.stringToDom(xmlInputData, xmlFilePath);
        	
        	File file = new File(xmlFilePath);
        	JAXBContext jaxbContext = JAXBContext.newInstance(Contracts.class);

        	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        	contract = (Contracts) jaxbUnmarshaller.unmarshal(file);    
		} catch (Exception e) {
			e.printStackTrace();
			//throw new CustomGenericException("Submitting Error: Failed to convert xml form data to contract model");
			throw new CustomGenericException("Илгээхэд алдаа гарлаа: XML мэдээллийг гэрээний загварт хөрвүүлж чадсангүй");
		}
    	
    	contract.setContract_status(INITIALIZE_STATUS);
    	contract.setOzd_File_name(ozd_filename);
    	
    	HashMap<String, String> uploadReturn = new HashMap<>();// return data
    	
    	if(!file_type.equals("") && file_type.equals("ozd")) {
    		logger.debug("Update prod_no :: " + contract.getProduct_number());
        	Contracts prevContract = contractDAO.getLatestContract(contract.getProduct_number());
        	if(prevContract != null){
        		contract.setCreated_id(prevContract.getCreated_id());
        	}
        	contract.setUpdated_id(authUser.getUsername());
        	
        	Date today = new Date(); 
        	contract.setUpdated_date(new Timestamp(today.getTime()));
        	
        	/**
        	 * If status is completed and user_role is reporter or manager
        	 * >> export ozd to pdf and upload to ftp server
        	 */
        	String pdfFileName = "";
        	if(prevContract.getContract_status().equals("C") && (hasRole(REPORTER) || hasRole(MANAGER))) {
        		contract.setContract_status(COMPLETED_STATUS);
        		
        		// Export ozd to pdf
        	    String ozdFilePath = filePath + File.separator + ozd_filename;
        	    pdfFileName = ozd_filename.replace(".ozd", ".pdf");
        	  
        	    String exportPdfFilePath = filePath;
        	    String fontsPath = System.getProperty("econtract.root") + "resources/fonts/";
        	    
        	    try {
        	    	OZExport.toPDF(fontsPath, ozdFilePath, exportPdfFilePath, (HttpServletRequest)request, response, pdfFileName);
				} catch (Exception e) {
					e.printStackTrace();
					//throw new CustomGenericException("Submitting Error: Failed to convert ozd file to pdf");
					throw new CustomGenericException("Илгээхэд алдаа гарлаа: ozd файлыг pdf болгож чадсангүй");
				}
        	    
    	    	// Upload pdf file to ftp server
    			FTPClient ftpClient = new FTPClient();
    			try {           	    	
        	    	String ftp_host = unitelWs_prop.getProperty("ftp.host");
        	    	String ftp_username = unitelWs_prop.getProperty("ftp.username");
        	    	String ftp_password = unitelWs_prop.getProperty("ftp.password");
        	    	int ftp_port = Integer.parseInt(unitelWs_prop.getProperty("ftp.port"));
        	    	
        	    	ftpClient.connect(ftp_host, ftp_port);
        	    	ftpClient.login(ftp_username, ftp_password);
        	    	   	    	
        	    	// Create contractId directory in FTP server
        	    	boolean dirExists = ftpClient.changeWorkingDirectory(contract.getProduct_number());
        	    	if(!dirExists) {
        	    		if(ftpClient.makeDirectory(contract.getProduct_number())) {
        	    			if(!ftpClient.changeWorkingDirectory(contract.getProduct_number())){
        	    				throw new IOException("Unable to change into newly created remote directory '" + contract.getProduct_number() + "'.  error='" + ftpClient.getReplyString()+"'");
        	    			}
        	    		}else {
        	    			throw new IOException("Unable to create remote directory '" + contract.getProduct_number() + "'.  error='" + ftpClient.getReplyString()+"'");
        	    		}
        	    		
        	    	}
			   				
        	        // Store pdf and ozd stream into ftp server
        	    	File exportedPdfFile = new File(exportPdfFilePath + File.separator + pdfFileName);
    				InputStream is = new FileInputStream(exportedPdfFile);
  
    				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    				ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
    				ftpClient.enterLocalPassiveMode();
    				
    				ftpClient.storeFile(pdfFileName, is);
    				is.close();
        			
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
    			
        	    contract.setPdf_File_name(pdfFileName);
        	}  
        	
        	// Save updated contract information to database
        	try {
        		int id = contractDAO.save(contract);
        		
        		logger.debug("End >> Insert inputformdata to database :: id = " + id);
        		uploadReturn.put("id", Integer.toString(id));
            	uploadReturn.put("contract_type", contract.getContract_type());
			} catch (Exception e) {
				e.printStackTrace();
				//throw new CustomGenericException("Submitting Error: Failed to update to database");
				throw new CustomGenericException("Илгээхэд алдаа гарлаа: Баазад өөрчлөлтийг оруулж чадсангүй");
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
    			throw new CustomGenericException("Илгээхэд алдаа гарлаа: Хуучин ozd файлуудыг устгаж чадсангүй");
    		}
        	
			return uploadReturn;
    	}else {        		
    		contract.setCreated_id(authUser.getUsername());
    		Date today = new Date(); 
        	contract.setCreated_date(new Timestamp(today.getTime()));
        	contract.setUpdated_date(new Timestamp(today.getTime()));
        	
        	// Save contract form's input data to database
        	try {
        		contractDAO.save(contract);
			} catch (Exception e) {
				e.printStackTrace();
				//throw new CustomGenericException("Submitting Error: Failed to update to database");
				throw new CustomGenericException("Илгээхэд алдаа гарлаа: Баазад өөрчлөлтийг оруулж чадсангүй");
			}
        	
        	return null;// Submit Success
    	}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/completed-list")
	public ModelAndView completedList() {
		JUserDetails authUser = (JUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("login_user_nm", authUser.getUsername());
		
		model.put("user_role", authUser.getAuthorities().get(0).toString());
		logger.debug("authUser.getAuthorities() :: " + authUser.getAuthorities().get(0).toString());
		
		return new ModelAndView(COMPLETED_LIST_VIEW, "model", model);		
	}
	
	@RequestMapping(value = "/completed-list/read", method = RequestMethod.POST)
    //public @ResponseBody List<Object> read() {
	public @ResponseBody List<Object> read(@RequestBody Map<String, Object> model) {
		JUserDetails authUser = (JUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 2016.08.11 : added by ryu.
		String tempSDate = (String)model.get("start");
		String tempEDate = (String)model.get("end");
		
		List<Contracts> list = new ArrayList<Contracts>(); 
		if (hasRole(TELLER)) {
			list = contractDAO.getLatestContractsGroupByContractId(authUser.getUsername(), tempSDate, tempEDate); 
		} else if (hasRole(ADMINISTRATOR)){
			list = contractDAO.getList(tempSDate, tempEDate);
		} else {
			list = contractDAO.getLatestContractsGroupByContractId(tempSDate, tempEDate);
		}
		
    	List<Object> _result = null;
		Map<String, Object> result = null;
		logger.debug("contract :: " + list.toString());
		for(Contracts contract : list) {
			if(result == null || result.size() == 0) {
				result = new HashMap<String, Object>();
			}
			
			result.put("contract_id", contract.getContract_id());
			result.put("contract_type", contract.getContract_type());
			result.put("last_name", contract.getLast_name());
			result.put("first_name", contract.getFirst_name());
			result.put("product_number", contract.getProduct_number());
			result.put("email", contract.getEmail());
			result.put("phone_no", contract.getPhone_number());
			result.put("contract_status", contract.getContract_status());
			
			if(contract.getContract_status().equals(INITIALIZE_STATUS)) {
				result.put("contract_status_name", "Баталгаажаагүй");
			} else if(contract.getContract_status().equals(COMPLETED_STATUS)) {
				result.put("contract_status_name", "Баталгаажсан");
			} else if(contract.getContract_status().equals(DECLINE_STATUS)) {
				result.put("contract_status_name", "Татгалзсан");
			}
			
			result.put("user_id", contract.getCreated_id());
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			result.put("updated_date", df.format(contract.getUpdated_date()));
			result.put("branch_name", contract.getBranch_name());
			result.put("ozd_file_name", contract.getOzd_File_name());
			
			String file_name = contract.getPdf_File_name();
			if(file_name == null){
				result.put("pdf_file_name_template", "");
			} else {
				result.put("pdf_file_name_template", "<input id='pdfbtn' type='button' class='pdf_button' onclick='openPDF(\""+ file_name +"\",\"" + contract.getProduct_number() + "\")'>");
			}
			
			if(_result == null || _result.size() == 0)
				_result = new ArrayList<Object>();
			
			_result.add(result);
			result = null;
		}
		
		return _result;
	}
	
	/**
	 * @return true if the user has one of the specified roles.
	 */
	protected static boolean hasRole(String role) {
	    boolean result = false;
	    for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
	        String userRole = authority.getAuthority();
	        
            if (role.equals(userRole)) {
                result = true;
                break;
            }
	    }

	    return result;
	}
	
	@ExceptionHandler(CustomGenericException.class)
	public ResponseEntity<String> handleCustomException(CustomGenericException ex) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
        return new ResponseEntity<String>(ex.getErrMsg(), headers, HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
}
