package fr.zcraft.zsurvey.commands;


import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zsurvey.zSurvey;
import fr.zcraft.zsurvey.zSurveyException;

@CommandInfo (name = "list")
public class ListCommand extends zSurveyCommands{
	
    @Override
    protected void run() throws CommandException {

        if(args.length > 0)
        	throwInvalidArgument("Would you mean '/survey " + super.getName() + "' ?");
        
        try {
        	zSurvey.list(playerSender());
        }catch(zSurveyException e) {
        	super.displayException(e);
        }
    }
}
