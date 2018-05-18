package fr.zcraft.zsurvey;

import java.util.Locale;

import fr.zcraft.zlib.components.configuration.Configuration;
import fr.zcraft.zlib.components.configuration.ConfigurationItem;
import static fr.zcraft.zlib.components.configuration.ConfigurationItem.item;

public class Config extends Configuration
{
	static public final ConfigurationItem<Locale> LANGUAGE = item("language", null);		//Definit la langue du plugin
	
    static public final ConfigurationItem<Integer> MAX_SURVEYS = item("max_surveys", 1);		//Definit le nombre max de sondage qu'une personne peut avoir
    static public final ConfigurationItem<Integer> MAX_QUESTIONS = item("max_questions", 10);	//Definit le nombre max de question par sondage
    static public final ConfigurationItem<Integer> MAX_ANSWERS = item("max_answers", 4);		//Definit le nombre max de reponse par question
}