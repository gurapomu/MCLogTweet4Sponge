package com.github.gurapomu.mcltSponge.twitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import com.github.gurapomu.mcltSponge.MCLTSponge;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Authorization {
	private static Twitter twitter;
	private static AccessToken accessToken = null;
	private static RequestToken requestToken = null;
	static String consumerKey = "FCjXJCBQfVlweT15kC3ZDg";
	static String consumerSecret = "id9yxgzmf0wtFls2rNPqVHOSt4rtRfq6cqjvtAdM";
	
	public static void mcltOAuth(){
		if(accessToken != null){	
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
			twitter.setOAuthAccessToken(accessToken);
		} else{	
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(consumerKey, consumerSecret);
			MCLTSponge.logger.info("Open the following URL and grant access to your account");
			MCLTSponge.logger.info(getOAuthURL());
			MCLTSponge.logger.info("Please enter command (/authpin [PIN])");
		}
	}
	public static void enterPIN(String pin){
		AccessToken accessTokenP = null;
		try{
			accessTokenP = twitter.getOAuthAccessToken(requestToken, pin);
		} catch(TwitterException e){
			if(401 == e.getStatusCode()){
				System.out.println("Unable to get the access token.");
			} else{
				e.printStackTrace();
			}
		}
		MCLTSponge.logger.info("Authorize successed");
		tweetString("Successfully authorization. (@" + loadAccessToken().getScreenName());
		storeAccessToken(accessTokenP);
	}
	public static void tweetString(String str){
		accessToken = loadAccessToken();
		try{
			if(accessToken != null){	
				twitter = new TwitterFactory().getInstance();
				twitter.setOAuthConsumer(consumerKey, consumerSecret);
				twitter.setOAuthAccessToken(accessToken);
			} else{
				MCLTSponge.logger.info("Please enter command(/oauth)");
				return;
			}
			
			Status status = twitter.updateStatus(str + " " + getTime());
			MCLTSponge.logger.info("Successfully updated the status to [" + status.getText() + "]");
			return;
		} catch(TwitterException e){
			e.printStackTrace();
			return;
		}
	}
	private static String getOAuthURL(){
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return requestToken.getAuthorizationURL();
	}
	private static void storeAccessToken(AccessToken accessTokenS){
		File f = createAccessTokenFileName();
		File d = f.getParentFile();
		if(!d.exists())	d.mkdirs();
		
		ObjectOutputStream os = null;
		try{
			os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(accessTokenS);
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			if(os != null){
				try{
					os.close();
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	private static File createAccessTokenFileName(){
		String str = System.getProperty("user.home") + "/.twitter/mclt/accessToken.dat";
		return new File(str);
	}
	public static AccessToken loadAccessToken(){
		File f = createAccessTokenFileName();
		ObjectInputStream is = null;
		try{
			is = new ObjectInputStream(new FileInputStream(f));
			AccessToken accessTokenL = (AccessToken) is.readObject();
			return accessTokenL;
		} catch(IOException e){
			return null;
		} catch(Exception e){
			return null;
		} finally{
			if(is != null){
				try{
					is.close();
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	private static String getTime(){
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH) + 1;
		int date = now.get(Calendar.DATE);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minit = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		
		if(hour >= 10 && minit >= 10 && second >= 10){
			return(month + "/" + date + " " + hour + ":" + minit + ":" + second);
		} else if(hour < 10 && minit >= 10 && second >= 10){
			return(month + "/" + date + " 0" + hour + ":" + minit + ":" + second);
		} else if(hour >= 10 && minit < 10 && second >= 10){
			return(month + "/" + date + " " + hour + ":0" + minit + ":" + second);
		} else if(hour >= 10 && minit >= 10 && second < 10){
			return(month + "/" + date + " " + hour + ":" + minit + ":0" + second);
		} else if(hour < 10 && minit < 10 && second >= 10){
			return(month + "/" + date + " 0" + hour + ":0" + minit + ":" + second);
		} else if(hour >= 10 && minit < 10 && second < 10){
			return(month + "/" + date + " " + hour + ":0" + minit + ":0" + second);
		} else if(hour < 10 && minit >= 10 && second < 10){
			return(month + "/" + date + " 0" + hour + ":" + minit + ":0" + second);
		} else{
			return(month + "/" + date + " 0" + hour + ":0" + minit + ":0" + second);
		}
	}
}
