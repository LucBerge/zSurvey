package fr.zcraft.zsurvey.commands;

import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zsurvey.zSurvey;
import fr.zcraft.zsurvey.zSurveyException;

@CommandInfo (name = "addanswer", usageParameters = "<name> <question_number> <answer>")
public class AddAnswerCommand extends zSurveyCommands{
	
    @Override
    protected void run() throws CommandException {
    	
        if (args.length < 3)
            throwInvalidArgument(I.t("A name, a question number and an answer are required."));
    	
        String name = args[0];
        int question_number = Integer.valueOf(args[1]);
    	
        String answer = "";
        for (int i = 2; i < args.length; i++)
        	answer += " " + args[i];
        
        try {
        	zSurvey.addAnswer(playerSender(), name, question_number, answer);
        }catch(zSurveyException e) {
        	super.displayException(e);
        }
    }
	
}
