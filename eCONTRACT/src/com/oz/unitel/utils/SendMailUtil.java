package com.oz.unitel.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;


@Service
public class SendMailUtil {

	private static final Logger logger = Logger.getLogger(SendMailUtil.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Resource(name="unitelWs")
	private Properties unitelWs_prop;
	
	public boolean sendMail(final String emailsenderFrom, final String subject, final String userMailAddress, final String mailFormName, final Map<String, String> bodyData, final Map<String, String> filesMap) {
		final String filePath = System.getProperty("econtract.root") + "resources/upload/";
		
		try {
			mailSender.send(new MimeMessagePreparator() {
			
				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {
					
					MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
					messageHelper.setFrom(emailsenderFrom);
					messageHelper.setTo(userMailAddress);
					messageHelper.setSubject(subject);
					
					Map<String, Object> model = new HashMap<String, Object>();
					String body = VelocityEngineUtils.mergeTemplateIntoString(
				               velocityEngine, "eCONTRACT.vm", "UTF-8", model);
					
					messageHelper.setText(body, true);
					
					if(filesMap != null) {
						String downfile = filedown(filesMap.get("prod_no"), filesMap.get("pdf_file_name")); 
						
						FileSystemResource attachFile = new FileSystemResource(filePath + File.separator + downfile);
						if (!attachFile.equals("")){
							messageHelper.addAttachment(attachFile.getFilename(), attachFile);
						}
						
					}
				}
			});
			logger.debug("e mail : "+userMailAddress + " send ok");
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("e : "+e.getLocalizedMessage());
			return false;
		}
	}
	
	public boolean sendMailWithoutAuthentication(final String emailsenderFrom, final String subject, final String customerName, final String userMailAddress, final String mailFormName, final Map<String, String> bodyData, final Map<String, String> filesMap) throws IOException {
		final String filePath = System.getProperty("econtract.root") + "resources";
		/*
		final String username = "ozglobal@forcs.com";
		final String password = "ozglobaladmin";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		*/
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", unitelWs_prop.getProperty("emailsender.auth"));
		props.put("mail.smtp.starttls.enable", unitelWs_prop.getProperty("emailsender.starttls"));
		props.put("mail.smtp.host", unitelWs_prop.getProperty("emailsender.host"));
		props.put("mail.smtp.ssl.trust", unitelWs_prop.getProperty("emailsender.host"));
		props.put("mail.smtp.port", unitelWs_prop.getProperty("emailsender.port"));
		
		Session session = Session.getDefaultInstance(props);
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailsenderFrom));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(userMailAddress));
			message.setSubject(subject);
			
			// Create email message content
			String msgContent = "<html>" + "\n";
			msgContent = msgContent + "<meta charset=\"UTF-8\">" + "\n";
			msgContent = msgContent + "<head></head>" + "\n";
			msgContent = msgContent + "<body>" + "\n";
			msgContent = msgContent + "<h4>Танд энэ өдрийн мэнд хүргэе!</h4>" + "\n";
			msgContent = msgContent + "<br>" + "\n";
			msgContent = msgContent + "<br>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Эрхэм хэрэглэгч " + customerName + " таны үйлчилгээний гэрээг хавсралтаар илгээлээ.</p>" + "\n";
			msgContent = msgContent + "<br>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Нэмэлт мэдээллийг</p>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">info@unitel.mn болон дараах дугаараас лавлана уу.</p>" + "\n";
			msgContent = msgContent + "<br>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Лавлах төв:</p>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Юнител-1414</p>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Юнивишн-1424</p>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Бусад оператор-77778888, 77778080 /төлбөртэй/</p>" + "\n";
			msgContent = msgContent + "<br>" + "\n";
			msgContent = msgContent + "<br>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Хүндэтгэсэн,</p>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Юнител групп</p>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Энэхүү мэдээлэл нь Юнител группын автомат системээс илгээгдэж байгаа тул хариу хүлээн авах боломжгүй юм.</p>" + "\n";
			msgContent = msgContent + "</body>" + "\n";
			msgContent = msgContent + "<html>";
						
			Multipart multipart = new MimeMultipart();
			
			MimeBodyPart htmlPart = new MimeBodyPart();
		    htmlPart.setContent(msgContent, "text/html; charset=utf-8");

		    multipart.addBodyPart(htmlPart);
			
			BodyPart body = new MimeBodyPart();
			
			if(filesMap != null) {
				String downfile = filedown(filesMap.get("prod_no"), filesMap.get("pdf_file_name")); 
				
				FileDataSource fDataSource = new FileDataSource(filePath + File.separator + "upload" + File.separator + downfile);
				if(fDataSource != null) {
					body.setDataHandler(new DataHandler(fDataSource));
					body.setFileName(downfile);
					multipart.addBodyPart(body);
				}
								
			}
			
			message.setContent(multipart, "text/html");
			
			// Send mail
			Transport.send(message);

			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean sendMailWithoutAuthenticationAndDownLoadFile(final String emailsenderFrom, final String subject, final String customerName, final String userMailAddress, final String mailFormName, final Map<String, String> bodyData, final Map<String, String> filesMap) throws IOException {
		final String filePath = System.getProperty("econtract.root") + "resources";
		/*
		final String username = "ozglobal@forcs.com";
		final String password = "ozglobaladmin";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		*/
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", unitelWs_prop.getProperty("emailsender.auth"));
		props.put("mail.smtp.starttls.enable", unitelWs_prop.getProperty("emailsender.starttls"));
		props.put("mail.smtp.host", unitelWs_prop.getProperty("emailsender.host"));
		props.put("mail.smtp.ssl.trust", unitelWs_prop.getProperty("emailsender.host"));
		props.put("mail.smtp.port", unitelWs_prop.getProperty("emailsender.port"));
		
		Session session = Session.getDefaultInstance(props);
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailsenderFrom));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(userMailAddress));
			message.setSubject(subject);
			
			// Create email message content
			String msgContent = "<html>" + "\n";
			msgContent = msgContent + "<meta charset=\"UTF-8\">" + "\n";
			msgContent = msgContent + "<head></head>" + "\n";
			msgContent = msgContent + "<body>" + "\n";
			msgContent = msgContent + "<h4>Танд энэ өдрийн мэнд хүргэе!</h4>" + "\n";
			msgContent = msgContent + "<br>" + "\n";
			msgContent = msgContent + "<br>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Эрхэм хэрэглэгч " + customerName + " таны үйлчилгээний гэрээг хавсралтаар илгээлээ.</p>" + "\n";
			msgContent = msgContent + "<br>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Нэмэлт мэдээллийг</p>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">info@unitel.mn болон дараах дугаараас лавлана уу.</p>" + "\n";
			msgContent = msgContent + "<br>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Лавлах төв:</p>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Юнител-1414</p>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Юнивишн-1424</p>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Бусад оператор-77778888, 77778080 /төлбөртэй/</p>" + "\n";
			msgContent = msgContent + "<br>" + "\n";
			msgContent = msgContent + "<br>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Хүндэтгэсэн,</p>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Юнител групп</p>" + "\n";
			msgContent = msgContent + "<p style=\"height: 12px;font-size: 14px;\">Энэхүү мэдээлэл нь Юнител группын автомат системээс илгээгдэж байгаа тул хариу хүлээн авах боломжгүй юм.</p>" + "\n";
			msgContent = msgContent + "</body>" + "\n";
			msgContent = msgContent + "<html>";
						
			Multipart multipart = new MimeMultipart();
			
			MimeBodyPart htmlPart = new MimeBodyPart();
		    htmlPart.setContent(msgContent, "text/html; charset=utf-8");

		    multipart.addBodyPart(htmlPart);
			
			BodyPart body = new MimeBodyPart();
			
			if(filesMap != null) {				
				FileDataSource fDataSource = new FileDataSource(filePath + File.separator + "upload" + File.separator + filesMap.get("pdf_file_name"));
				if(fDataSource != null) {
					body.setDataHandler(new DataHandler(fDataSource));
					body.setFileName(filesMap.get("pdf_file_name"));
					multipart.addBodyPart(body);
				}
								
			}
			
			message.setContent(multipart, "text/html");
			
			// Send mail
			Transport.send(message);

			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	// Download file from FTP server
	public String filedown(String prod_no, String pdf_filename) throws IOException{
		String filePath = System.getProperty("econtract.root") + "resources/upload/";

		FTPClient ftpClient = new FTPClient();
		try {
	    	
	    	String ftp_host = unitelWs_prop.getProperty("ftp.host");
	    	String ftp_username = unitelWs_prop.getProperty("ftp.username");
	    	String ftp_password = unitelWs_prop.getProperty("ftp.password");
	    	int ftp_port = Integer.parseInt(unitelWs_prop.getProperty("ftp.port"));
	    	
	    	ftpClient.connect(ftp_host, ftp_port);
	    	ftpClient.login(ftp_username, ftp_password);
	    	
	    	String remoteFile = "/" + prod_no + "/" + pdf_filename;

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
	    
	    return pdf_filename;// Download Success
	}
}
