package com.oz.unitel.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author PHAM
 *  Dec 11, 2014 
 * 
 */
public class OZExport {
	public static boolean toPDF(String _fontsPath, String _ozdFilePath, String _exportPdfFilePath,
			HttpServletRequest _request, HttpServletResponse _response,
			String _exportPdfFileName) throws ServletException, IOException {

		//Check ozd file is existed or not
		File f = new File(_ozdFilePath);
		if (f.exists() && !f.isDirectory()) {
			
			String exportFormat = "pdf";
			String strClient = _request.getHeader("user-agent");
			
			//This is metadata need to define
			String viewerFilePath = "j:\\";
			String exportResultKey = viewerFilePath + _exportPdfFileName;
			
			//Create OZ's Applet viewer export params
			Hashtable<String, String> param = new Hashtable<String, String>();
			param.put("connection.openfile", _ozdFilePath);
			param.put("client_browser", strClient);
			param.put("export.format", exportFormat);
			param.put(exportFormat + ".path", viewerFilePath);
			param.put(exportFormat + ".filename", _exportPdfFileName);
			param.put("export.mode", "silent");
			param.put("applet.mode", "export");
			param.put("applet.useprogressbar", "false");
			param.put("applet.allowmultiframe", "true");
			param.put("export.confirmsave", "false");
			param.put("applet.showerrormessage", "false");
			param.put("export.saveonefile", "true");
			param.put("connection.fetchtype", "BATCH");
			param.put("information.debug", "true");
			param.put("pdf.fontembedding", "true");			
			param.put("pdf.savecomment", "true");
			param.put("font.fontnames", "font1,font2");
			param.put("font.font1.url", _fontsPath + "NotoSans-Regular.ttf");
			param.put("pfont.font1.name", "NotoSans-Regular");
			param.put("font.font2.url", _fontsPath + "NotoSans-Bold.ttf");
			param.put("font.font2.name", "NotoSans-Bold");
			
			_request.setAttribute("OZViewerExportParam", param);
			String movePage = "/server";
			RequestDispatcher dispatcher = _request.getRequestDispatcher(movePage);
			dispatcher.include(_request, _response);

			Object o = _request.getAttribute("OZViewerExportResult");
			if (o == null) {
				Throwable t = (Throwable) _request
						.getAttribute("OZViewerExportError");
				t.printStackTrace();
				
				return false;
			} else {
				Hashtable t = (Hashtable) o;
				if (exportFormat.equalsIgnoreCase("PDF")) {					
					byte[] b = (byte[]) t.get(exportResultKey);
					if (b != null) {
						FileOutputStream fos = null;
						try {
							fos = new FileOutputStream(_exportPdfFilePath
									+ File.separator + _exportPdfFileName);
							fos.write(b);
							fos.flush();
						} catch (Exception e) {
							return false;
						} finally {
							if (fos != null) {
								fos.close();
							}
						}

						File file = new File(_exportPdfFilePath + File.separator
								+ _exportPdfFileName);
						if (!file.exists()) {
							return false;
						} else {
							return true;
						}

					} else {
						return false;
					}

				}
			}
		}else {
			return false;
		}
		
		return true;
	}
	
	public static boolean toXML(String _ozdFilePath, String _exportXmlFilePath,
			HttpServletRequest _request, HttpServletResponse _response,
			String _exportXmlFileName) throws ServletException, IOException {

		//Check ozd file is existed or not
		File f = new File(_ozdFilePath);
		if (f.exists() && !f.isDirectory()) {
			
			String exportFormat = "eform_xml";
			String strClient = _request.getHeader("user-agent");
			
			//This is metadata need to define
			String viewerFilePath = "j:\\";
			String exportResultKey = viewerFilePath + _exportXmlFileName;
			
			//Create OZ's Applet viewer export params
			Hashtable<String, String> param = new Hashtable<String, String>();
			param.put("connection.openfile", _ozdFilePath);
			param.put("client_browser", strClient);
			param.put("export.format", exportFormat);
			param.put(exportFormat + ".path", viewerFilePath);
			param.put(exportFormat + ".filename", _exportXmlFileName);
			param.put("export.mode", "silent");
			param.put("applet.mode", "export");
			param.put("applet.useprogressbar", "false");
			param.put("applet.allowmultiframe", "true");
			param.put("export.confirmsave", "false");
			param.put("applet.showerrormessage", "false");
			param.put("export.saveonefile", "true");
			param.put("connection.fetchtype", "BATCH");
			param.put("information.debug", "debug");
			
			_request.setAttribute("OZViewerExportParam", param);
			String movePage = "/server";
			RequestDispatcher dispatcher = _request.getRequestDispatcher(movePage);
			dispatcher.include(_request, _response);

			Object o = _request.getAttribute("OZViewerExportResult");
			if (o == null) {
				Throwable t = (Throwable) _request
						.getAttribute("OZViewerExportError");
				t.printStackTrace();
				
				return false;
			} else {
				Hashtable t = (Hashtable) o;
				if (exportFormat.equalsIgnoreCase("eform_xml")) {
					
					byte[] b = (byte[]) t.get(exportResultKey);
					if (b != null) {
						FileOutputStream fos = null;
						try {
							fos = new FileOutputStream(_exportXmlFilePath
									+ File.separator + _exportXmlFileName);
							fos.write(b);
							fos.flush();
						} catch (Exception e) {
							return false;
						} finally {
							if (fos != null) {
								fos.close();
							}
						}

						File file = new File(_exportXmlFilePath + File.separator
								+ _exportXmlFileName);
						if (!file.exists()) {
							return false;
						} else {
							return true;
						}

					} else {
						return false;
					}

				}
			}
		}else {
			return false;
		}
		
		return true;
	}
}
