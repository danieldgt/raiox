package br.com.raiox.filter;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * @author daniel
 *
 */

public class SessionListener implements HttpSessionListener {

  private static int totalActiveSessions;
	
  public static int getTotalActiveSession(){
	return totalActiveSessions;
  }
	
  @Override
  public void sessionCreated(HttpSessionEvent arg0) {
	totalActiveSessions++;
	System.out.println("sessionCreated - add one session into counter");
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent arg0) {
	totalActiveSessions--;
	System.out.println("sessionDestroyed - deduct one session from counter");
  }	
}