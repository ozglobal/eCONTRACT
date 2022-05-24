package com.oz.unitel.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oz.unitel.security.JUserDetails;
import com.oz.unitel.utils.CustomGenericException;

@Controller
public class LoginController {
	
	@Resource(name="unitelWs")
	private Properties unitelWs_prop;
	
	private static final String LOGIN_VIEW = "login";
	
	private static final String M_LOGIN_VIEW = "m-login";
	private static final String M_INDEX_VIEW = "m-index";
	private static final String M_COMPLETED_LIST = "m-completed-list";
	
	final static Logger logger = Logger.getLogger(LoginController.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/*")
	public ModelAndView allEntrance(HttpServletRequest request) {		
		
		String userAgent = request.getHeader("user-agent");
		String viewName = null;
		
		boolean isMobileAgent = false;
	    if(userAgent.indexOf("Mobile") != -1 || userAgent.indexOf("Android") != -1){
	    	isMobileAgent = true;
	    }
	    
	    if(isMobileAgent){
	    	viewName = LOGIN_VIEW;
	    }else{
	    	viewName = LOGIN_VIEW;
	    }
	    
		return new ModelAndView(viewName);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/login")
    public ModelAndView login(@RequestParam(required=false) boolean error, @RequestParam(required=false) boolean expired,
    		@RequestParam (required = false) boolean logout, @RequestParam(required=false) boolean missingrole, HttpServletRequest request){     
		
		ModelAndView mav = new ModelAndView();
		
	    String userAgent = request.getHeader("user-agent");
		String viewName = null;
		String title = null;
		
		boolean isMobileAgent = false;
	    if(userAgent.indexOf("Mobile") != -1 || userAgent.indexOf("Android") != -1){
	    	isMobileAgent = true;
	    }
	    
	    if(isMobileAgent){
	    	/*viewName = M_LOGIN_VIEW;*/
	    	viewName = LOGIN_VIEW;
	    	title = "M-Login";
	    }else{
	    	viewName = LOGIN_VIEW;
	    	title = "Sign In";
	    }
	    
	    mav.setViewName(viewName);
    	mav.addObject("title", title);
	    
		if(error){
			mav.addObject("error", error);					
		}
		
		if(expired){
			mav.addObject("expired", expired);					
		}
		
		if(logout){
			mav.addObject("logout", logout);
		}
		
		if(missingrole){
			mav.addObject("missingrole", missingrole);
		}
		
		return mav;                   
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/m-index")
	public ModelAndView mIndex() {		
		JUserDetails authUser = (JUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("accountName", authUser.getUsername());		
		
		return new ModelAndView(M_INDEX_VIEW, "model", model);		
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/m-login")
	public ModelAndView mLogin() {		
		ModelAndView mav = new ModelAndView();
		     	
    	mav.setViewName(M_LOGIN_VIEW);
     	mav.addObject("title", "M-Login");
     	
 		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/m-completed-list")
	public ModelAndView mobileCompletedList() {
		JUserDetails authUser = (JUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Map<String, Object> model = new HashMap<String, Object>();
        model.put("role", authUser.getAuthorities());
		model.put("accountName", authUser.getFullName());
		
		return new ModelAndView(M_COMPLETED_LIST, "model", model);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/m-completed-list/read")
	public @ResponseBody ArrayList<HashMap<String, Object>> readCompletedList(@RequestParam("prod_no") String prodNo) {
		
		List<HashMap<String, Object>> completedList = new ArrayList<HashMap<String, Object>>();
		
		// Search all pdf file in contractid directory
		FTPClient ftpClient = new FTPClient();
		try {
	    	
	    	String ftp_host = unitelWs_prop.getProperty("ftp.host");
	    	String ftp_username = unitelWs_prop.getProperty("ftp.username");
	    	String ftp_password = unitelWs_prop.getProperty("ftp.password");
	    	int ftp_port = Integer.parseInt(unitelWs_prop.getProperty("ftp.port"));
	    	
	    	ftpClient.connect(ftp_host, ftp_port);
	    	ftpClient.login(ftp_username, ftp_password);
	    	
	    	String dirToSearch = prodNo;
	    	FTPFileFilter fileFilter = new FTPFileFilter() {
				
				@Override
				public boolean accept(FTPFile ftpFile) {
					return (ftpFile.isFile() && ftpFile.getName().endsWith(".pdf"));
				}
			};
			
			FTPFile[] result = ftpClient.listFiles(dirToSearch, fileFilter);
			if (result != null && result.length > 0 ) {
				int _index = 1;
				for (FTPFile aFile : result) {
					HashMap<String, Object> _result_map = new HashMap<>();
					_result_map.put("doc_name", aFile.getName());
					_result_map.put("prod_no", dirToSearch);
					_result_map.put("index_no", _index);
					
					completedList.add(_result_map);
					_index ++;
				}
			}
			
		} catch (Exception e) {
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
		
		return (ArrayList<HashMap<String, Object>>) completedList;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/m-pdfviewer")
	public void mPdfViewerJ(@RequestParam("file_name") String fileName, @RequestParam("prod_no") String prodNo, HttpServletResponse response) {
		
		//Download file to FTP server 
		FTPClient ftpClient = new FTPClient();
		try {
	    	
	    	String ftp_host = unitelWs_prop.getProperty("ftp.host");
	    	String ftp_username = unitelWs_prop.getProperty("ftp.username");
	    	String ftp_password = unitelWs_prop.getProperty("ftp.password");
	    	int ftp_port = Integer.parseInt(unitelWs_prop.getProperty("ftp.port"));
	    	
	    	ftpClient.connect(ftp_host, ftp_port);
	    	ftpClient.login(ftp_username, ftp_password);
	    	
	    	String remoteFile = "/" + prodNo + "/" + fileName;	    	

	    	ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
	    	
	    	InputStream is = ftpClient.retrieveFileStream(remoteFile);
	    	
	    	org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
		    response.flushBuffer();

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
	}
	
}
