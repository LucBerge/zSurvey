package fr.zcraft.zsurvey.commands;

import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zsurvey.zSurvey;
import fr.zcraft.zsurvey.zSurveyException;

@CommandInfo (name = "addquestion", usageParameters = "<name> <question>")
public class AddQuestionCommand extends zSurveyCommands{
	
    @Override
    protected void run() throws CommandException {
    	
        if (args.length < 2)
            throwInvalidArgument(I.t("A survey name and a question are required."));
    	        
        String name = args[0];
        
        String question = "";
        for (int i = 1; i < args.length; i++)
        	question += " " + args[i];
        
        try {
        	zSurvey.addQuestion(playerSender(), name, question);
        }catch(zSurveyException e) {
        	super.displayException(e);
        }
    }
	
}
