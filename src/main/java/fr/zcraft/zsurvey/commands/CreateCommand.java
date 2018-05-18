package fr.zcraft.zsurvey.commands;

import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zsurvey.zSurvey;
import fr.zcraft.zsurvey.zSurveyException;
import fr.zcraft.zsurvey.survey.Survey;

@CommandInfo (name = "create", usageParameters = "<name> <description>")
public class CreateCommand extends zSurveyCommands{
	
    @Override
    protected void run() throws CommandException {
    	    	
        if (args.length < 2)
            throwInvalidArgument(I.t("A survey name and a description are required."));
    	
        String name = args[0];
        
        String description = "";
        for (int i = 1; i < args.length; i++)
        	description += " " + args[i];
        
        try {
        	Survey survey = new Survey(name, playerSender(), description);
        	zSurvey.create(playerSender(), survey);
        }catch(zSurveyException e) {
        	super.displayException(e);
        }
    }
}
