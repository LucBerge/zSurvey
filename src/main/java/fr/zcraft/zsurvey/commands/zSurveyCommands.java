package fr.zcraft.zsurvey.commands;

import fr.zcraft.zlib.components.commands.Command;
import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zsurvey.zSurveyException;

abstract public class zSurveyCommands extends Command{

	public void displayException(zSurveyException e) throws CommandException {
		switch(e.getReason())
	    {
	        case ALREADY_EXIST:
	    		error(I.t("This survey name already exists."));
	        	break;
		        
			case NON_AUTHOR:
				 error(I.t("You aren't the survey's author."));
				 break;
		       	
		    case UNSTARTED:
	    		warning(I.t("This survey isn't opened yet."));
		       	break;
				
		    case IN_PROGRESS:
	    		warning(I.t("This survey is in progress."));
		       	break;
		       	
		    case FINISHED:
	    		warning(I.t("This survey is unfortunaly closed."));
		       	break;
		       	
	    	case UNKNOW_SURVEY:
	    		warning(I.t("This survey doesn't exist."));
	    		break;
	    		
	    	case UNKNOW_QUESTION:
	    		warning(I.t("This question doesn't exist."));
	    		break;
	    		
	    	case UNKNOW_ANSWER:
	    		warning(I.t("This answer doesn't exist."));
	    		break;
	        	
		    case MAX_SURVEYS:
	    		warning(I.t("Maximum of surveys reached. \"/survey remove <name>\" to remove an old one."));
		       	break;
		       	
		    case MAX_QUESTIONS:
	    		warning(I.t("Maximum of questions reached. \"/survey rmquestion\" to remove an old one."));
		       	break;     	

		    case MAX_ANSWERS:
	    		warning(I.t("Maximum of answers reached. \"/survey rmanswer\" to remove an old one."));
		       	break;
		       	
		    case MIN_QUESTIONS:
		    	error(I.t("A minimum of 1 question is requiered to start a survey."));
		       	break;
		       	
		    case MIN_ANSWERS:
		    	error(I.t("A minimum of 2 answers per question are requiered to start a survey."));
		       	break;
		       			       	
		    default:
	    		error(e.getMessage());
	    		break;
	    }
	}
}
