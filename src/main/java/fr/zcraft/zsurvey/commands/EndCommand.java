package fr.zcraft.zsurvey.commands;

import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zsurvey.zSurvey;
import fr.zcraft.zsurvey.zSurveyException;

@CommandInfo (name = "end", usageParameters = "<name>")
public class EndCommand extends zSurveyCommands{
	
    @Override
    protected void run() throws CommandException {
    	
        if (args.length < 1)
            throwInvalidArgument(I.t("A name is required."));
        else if(args.length > 1)
        	playerSender().sendMessage("Would you mean '/survey end " + args[0] + "' ?");
    	
        String name = args[0];
        
        try {
        	zSurvey.end(playerSender(), name);
        }catch(zSurveyException e) {
        	super.displayException(e);
        }
    }
	
}
