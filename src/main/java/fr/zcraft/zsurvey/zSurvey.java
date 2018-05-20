package fr.zcraft.zsurvey;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.zcraft.zlib.components.commands.Commands;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.components.i18n.I18n;
import fr.zcraft.zlib.core.ZPlugin;
import fr.zcraft.zlib.tools.text.RawMessage;
import fr.zcraft.zsurvey.commands.*;
import fr.zcraft.zsurvey.survey.Survey;

public final class zSurvey extends ZPlugin implements Listener{

	private static Map<String,Survey> surveys = new HashMap<>();
	
	  /**********/
	 /* ON/OFF */
	/**********/
	
	@Override
    public void onEnable() {
		
		saveDefaultConfig();										//Charge config.yml
        loadComponents(Commands.class, Config.class, I18n.class);	//Charge les classes suivantes

        I18n.setPrimaryLocale(Config.LANGUAGE.get());				//Definit la langue utilisée
        this.getServer().getPluginManager().registerEvents(this, this);
        
        Commands.register("survey", ListCommand.class, VoteCommand.class, CreateCommand.class, AddQuestionCommand.class, AddAnswerCommand.class, StartCommand.class, SeeCommand.class, EndCommand.class, RemoveCommand.class, RemoveQuestionCommand.class, RemoveAnswerCommand.class);	//Charge les commandes suivantes
        Commands.registerShortcut("survey", ListCommand.class, "surveys");
		//Deserialize les sondages en cours
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
    	list(e.getPlayer());	//Affiche la liste des sondages en cours et termines
	}
	
    @Override
    public void onDisable() {        
    	//Serialise les sondages en cours
    }
    
	  /************/
	 /* COMMANDS */
	/************/
	
	/** Affiche la liste des sondages en cours et termines **/
	public static void list(Player sender) {
		/*
        if (Permissions.USER.grantedTo(sender))
        	sender.sendMessage("zsurvey.user -> ok");
        if (Permissions.ADMIN.grantedTo(sender))
        	sender.sendMessage("zsurvey.admin -> ok");
        else
        	sender.sendMessage("no permissions");*/
		
		if(!surveys.isEmpty()) {
			sender.sendMessage(I.t("{darkgreen}There is {0} survey(s) in progress :", surveys.size()));			
			for (Map.Entry<String, Survey> entry : surveys.entrySet())
				RawMessage.send(sender,entry.getValue().toRawText());				
			sender.sendMessage(I.t("{gold}Click on a survey to vote."));
		}
		else
			sender.sendMessage(I.t("{darkgreen}There is no surveys in progress."));
	}
	
	/** Participer à un sondage **/
	public static void vote(Player sender, String name, int question_number, int answer_number) {	
		if(surveys.get(name) == null)											//Si le sondage est inexistant
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_SURVEY);	//Exception
		//Permission
		surveys.get(name).vote(sender, question_number, answer_number);
	}
	
	/** Creer un sondage **/
	public static void create(Player sender, Survey survey) {	
		if(surveys.get(survey.getName()) != null)									//Si un sondage possede le meme nom
				throw new zSurveyException(zSurveyException.Reason.ALREADY_EXIST);	//Exception
		if(surveys.size() >= Config.MAX_SURVEYS.get())								//Si le nombre max de sondage a été atteind
			throw new zSurveyException(zSurveyException.Reason.MAX_SURVEYS);		//Exception
		//Permissions
		surveys.put(survey.getName(), survey);
		sender.sendMessage(survey.toString());
	}
	
	/** Ajouter une question a un sondage **/
	public static void addQuestion(Player sender, String name, String question) {
		if(surveys.get(name) == null)											//Si le sondage est inexistant
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_SURVEY);	//Exception
		//Permission
		surveys.get(name).addQuestion(sender, question);
	}
	
	/** Ajouter une reponse a une question d'un sondage **/
	public static void addAnswer(Player sender, String name, int question_number, String answer) {
		if(surveys.get(name) == null)											//Si le sondage est inexistant
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_SURVEY);	//Exception
		//Permission
		surveys.get(name).addAnswer(sender, question_number, answer);
	}
	
	/** Lancer un sondage **/
	public static void start(Player sender, String name) {
		if(surveys.get(name) == null)											//Si le sondage est inexistant
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_SURVEY);	//Exception
		//Permission
		surveys.get(name).start(sender);
	}
	
	/** Voir OU voir les resultats d'un sondage **/
	public static void see(Player sender, String name) {
        		
		if(surveys.get(name) == null)											//Si le sondage est inexistant
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_SURVEY);	//Exception
		//Permission
		surveys.get(name).see(sender);
	}
	
	/** Cloturer un sondage **/
	public static void end(Player sender, String name) {
		if(surveys.get(name) == null)											//Si le sondage est inexistant
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_SURVEY);	//Exception
		//Permission
		surveys.get(name).end(sender);
	}
	
	/** Supprimer un sondage **/
	public static void remove(Player sender, String name) {
		if(surveys.get(name) == null)											//Si le sondage est inexistant
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_SURVEY);	//Exception
		if(!surveys.get(name).getAuthor().equals(sender))						//Si la sondage n'est pas l'auteur
			throw new zSurveyException(zSurveyException.Reason.NON_AUTHOR);		//Exception
		//Permissions
		surveys.remove(name);
		sender.sendMessage(I.t("{darkgreen}Survey '{0}' have been removed.", name));
	}
	
	/** Supprimer une question d'un sondage **/
	public static void removeQuestion(Player sender, String name, int question_number) {
		if(surveys.get(name) == null)											//Si le sondage est inexistant
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_SURVEY);	//Exception
		//Permission
		surveys.get(name).removeQuestion(sender, question_number);
	}

	/** Supprimer une reponse d'une question d'un sondage **/
	public static void removeAnswer(Player sender, String name, int question_number, int answer_number) {	
		if(surveys.get(name) == null)											//Si le sondage est inexistant
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_SURVEY);	//Exception
		//Permission
		surveys.get(name).removeAnswer(sender, question_number, answer_number);
	}
}
