package fr.zcraft.zsurvey.commands;

import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zsurvey.zSurvey;
import fr.zcraft.zsurvey.zSurveyException;

@CommandInfo (name = "removequestion", usageParameters = "<name> <question_number>")
public class RemoveQuestionCommand extends zSurveyCommands{
	
    @Override
    protected void run() throws CommandException {
    	
        if (args.length < 2)
            throwInvalidArgument(I.t("A name and a question number are required."));
        else if(args.length > 2)
        	throwInvalidArgument("Would you mean '/survey " + super.getName() + " "  + args[0] + " "  + args[1] + "' ?");
    	
        String name = args[0];
        int question_number = Integer.valueOf(args[1]);
        
        try {
        	zSurvey.removeQuestion(playerSender(), name, question_number);
        }catch(zSurveyException e) {
        	super.displayException(e);
        }
    }
	
}
