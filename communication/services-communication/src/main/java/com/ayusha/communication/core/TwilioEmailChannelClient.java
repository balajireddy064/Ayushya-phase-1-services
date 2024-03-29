package com.ayusha.communication.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;



/**
 * 
 * @author Author
 * Date:01-Aug-2019
 * Communication Service Methods
 *
 */
@Component	
public class TwilioEmailChannelClient implements INotificationChannel{
	
	
	
	
	/** fromAddress  **/
	private String fromAddress="";
	
	/**password  **/
	private String password="";
	
	/** host **/
	private String host="";
	
	/** propEmailConfigs **/
	private Properties propEmailConfigs = null;
	/**
	 * default constructor
	 */
	public TwilioEmailChannelClient() {
		
	}
	
	/**
	 * sendNotification
	 */
	public String sendNotification(HashMap hashMapMessageProperties) throws NotificationOperationException{	
		
		Session mailSession = null;
		MimeMessage emailMessage = null;
		String toAddresses = "";
		Transport transport = null;
		List<String> toAddressesList = null;
		int iCount =0;
		try {
			toAddresses = ""+hashMapMessageProperties.get("TO");
		
			System.out.println(" CHANGES "+propEmailConfigs);
			mailSession = Session.getDefaultInstance(propEmailConfigs, null);
			emailMessage = new MimeMessage(mailSession);
			toAddressesList = toAddress(toAddresses);
			
			iCount = toAddressesList.size();
			
			for (int index = 0; index <iCount; index++) {
				emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddressesList.get(index)));
			}
	
			emailMessage.setSubject(""+hashMapMessageProperties.get("SUBJECT"));
			emailMessage.setContent(hashMapMessageProperties.get("MESSAGE"),"text/html");
			transport = mailSession.getTransport("smtp");
			System.out.println(" EMAIL IS "+fromAddress+"::::"+password+"::::::"+host+"::::::"+transport);
			transport.connect(host, fromAddress, password);
			
			
		    transport.sendMessage(emailMessage, emailMessage.getAllRecipients());//emailMessage.getAllRecipients());
			transport.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Email sent successfully.");
		return "SUCCESS";
	}
	
	/**
	 * toAddress
	 * @param toAddresses
	 * @return lstToAddresses
	 */
	private List<String> toAddress(String toAddresses){
		List<String> lstToAddresses = new ArrayList<String>();
		StringTokenizer stringTokenizer = new StringTokenizer(toAddresses,";");
		
		while(stringTokenizer.hasMoreElements()) {
			lstToAddresses.add(""+stringTokenizer.nextToken());
		}
		return lstToAddresses;
	}

	

	/**
	 * @return the fromAddress
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	 * @param fromAddress the fromAddress to set
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the propEmailConfigs
	 */
	public Properties getPropEmailConfigs() {
		return propEmailConfigs;
	}

	/**
	 * @param propEmailConfigs the propEmailConfigs to set
	 */
	public void setPropEmailConfigs(Properties propEmailConfigs) {
		this.propEmailConfigs = propEmailConfigs;
	}

	
}
