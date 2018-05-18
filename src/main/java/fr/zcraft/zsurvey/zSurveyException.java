package fr.zcraft.zsurvey;

public class zSurveyException extends RuntimeException{

    private Reason reason;

    public zSurveyException(Reason reason)
    {
    	super();
        this.reason = reason;
    }

	  /***********/
	 /* GETTERS */
	/***********/
    
    public Reason getReason()
    {
    	return this.reason;
    }

	  /***************/
	 /* ENUMERATION */
	/***************/
    
    public enum Reason
    {
    	ALREADY_EXIST,
    	NON_AUTHOR,
    	UNSTARTED, IN_PROGRESS, FINISHED,
    	UNKNOW_SURVEY, UNKNOW_QUESTION, UNKNOW_ANSWER,
        MAX_SURVEYS, MAX_QUESTIONS, MAX_ANSWERS,
        MIN_QUESTIONS, MIN_ANSWERS; 
    }
}
