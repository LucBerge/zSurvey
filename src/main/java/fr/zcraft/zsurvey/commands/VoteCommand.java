package fr.zcraft.zsurvey.commands;


import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zsurvey.zSurvey;
import fr.zcraft.zsurvey.zSurveyException;

@CommandInfo (name = "vote", usageParameters = "<name> <question_number> <answer_number>")
public class VoteCommand extends zSurveyCommands{
	
    @Override
    protected void run() throws CommandException {
    	
        if (args.length < 3)
            throwInvalidArgument(I.t("A survey name, a question number and an answer number are required."));
        else if(args.length > 3)
        	throwInvalidArgument("Would you mean '/survey " + super.getName() + " " + args[0] + " " + args[1] + " " + args[2] + "' ?");
    	
        String name = args[0];
        int question_number = Integer.valueOf(args[1]);
        int answer_number = Integer.valueOf(args[2]);
    	
        try {
        	zSurvey.vote(playerSender(), name, question_number, answer_number);
        }catch(zSurveyException e) {
        	super.displayException(e);
        }
    }
}
