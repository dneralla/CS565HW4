/*
 * Copyright 2007-2012 Amazon Technologies, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 * 
 * http://aws.amazon.com/apache2.0
 * 
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */ 




import java.io.File;

import com.amazonaws.mturk.addon.HITQuestion;
import com.amazonaws.mturk.requester.Comparator;
import com.amazonaws.mturk.requester.HIT;
import com.amazonaws.mturk.requester.Locale;
import com.amazonaws.mturk.requester.QualificationRequirement;
import com.amazonaws.mturk.service.axis.RequesterService;
import com.amazonaws.mturk.util.PropertiesClientConfig;

/**
 * The Simple Survey sample application will create a HIT asking a worker to indicate their 
 * political party preferences.
 * 
 * mturk.properties must be found in the current file path.
 * 
 * The following concepts are covered:
 * - File based QAP HIT loading
 * - Using a locale qualification
 */
public class SimpleSurvey {

	private RequesterService service;
	
  //Defining the attributes of the HIT
  private String title = "Choose the offensive words(Filtering purposes)";
  private String description = "Filtering Survey.";
  private int numAssignments = 1;
  private double reward = 0.02;
  private String keywords = "sample, SDK, survey";
  private long assignmentDurationInSeconds = 60 * 60; // 1 hour
  private long autoApprovalDelayInSeconds = 60 * 60 * 24 * 15; // 15 days
  private long lifetimeInSeconds = 60 * 60 * 24 * 3; // 3 days
  private String requesterAnnotation = "sample#survey";
  
  //Defining the location of the externalized question (QAP) file.
  private String rootDir = ".";
  private String questionDir = "surveyques";
  
  /**
   * Constructor
   *
   */
  public SimpleSurvey() {
    service = new RequesterService(new PropertiesClientConfig("mturk.properties"));
  }
	
  /**
   * Checks to see if there are sufficient funds in your account to run this sample.
   * @return true if there are sufficient funds.  False if not.
   */
  public boolean hasEnoughFund() {
    double balance = service.getAccountBalance();
    System.out.println("Got account balance: " + RequesterService.formatCurrency(balance));
    return balance > 0;
  }
  
  /**
   * Creates the simple survey.
   *
   */
  public void createSimpleSurvey() {
    try {
      QualificationRequirement qualReq = new QualificationRequirement();
      qualReq.setQualificationTypeId(RequesterService.LOCALE_QUALIFICATION_TYPE_ID);
      qualReq.setComparator(Comparator.EqualTo);
      Locale country = new Locale();
      country.setCountry("US");
      qualReq.setLocaleValue(country);
      QualificationRequirement[] qualReqs = null;
      qualReqs = new QualificationRequirement[] { qualReq };
      File quesDir = new File(questionDir);
      
      for ( File file : quesDir.listFiles()) {
    	 
    	  String filePath = questionDir + File.separator + file.getName();
    	  System.out.println("Posting HIT of "+ filePath);
    	  HITQuestion question = new HITQuestion(file.getCanonicalPath());
          HIT hit = service.createHIT(null, // HITTypeId 
              title, 
              description, keywords, 
              question.getQuestion(),
              reward, assignmentDurationInSeconds,
              autoApprovalDelayInSeconds, lifetimeInSeconds,
              numAssignments, requesterAnnotation, 
              qualReqs,
              null // responseGroup
            );
          
          System.out.println("Created HIT: " + hit.getHITId());

          System.out.println("You may see your HIT with HITTypeId '" 
              + hit.getHITTypeId() + "' here: ");
          
          System.out.println(service.getWebsiteURL() 
              + "/mturk/preview?groupId=" + hit.getHITTypeId());
          
          //Demonstrates how a HIT can be retrieved if you know its HIT ID
          HIT hit2 = service.getHIT(hit.getHITId());
          
          System.out.println("Retrieved HIT: " + hit2.getHITId());
          
          if (!hit.getHITId().equals(hit2.getHITId())) {
            System.err.println("The HIT Ids should match: " 
                + hit.getHITId() + ", " + hit2.getHITId());
          }
        }
    } catch (Exception e) {
        System.err.println(e.getLocalizedMessage());
      }  
      
  }
  
	/**
   * @param args
   */
	public static void main(String[] args) {

    SimpleSurvey app = new SimpleSurvey();

    if (app.hasEnoughFund()) {
      app.createSimpleSurvey();
    }
  }
}
