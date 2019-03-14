package fr.zcraft.zsurvey.survey;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import fr.zcraft.zlib.components.rawtext.RawText;
import fr.zcraft.zlib.tools.text.RawMessage;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zsurvey.Config;
import fr.zcraft.zsurvey.Permissions;
import fr.zcraft.zsurvey.zSurveyException;
import fr.zcraft.zsurvey.commands.SeeCommand;

public class Survey{

	private String name;
	private Player author;
	private String description;
	private ArrayList<Question> questions;
	private SurveyState state;
	
	public Survey(String name, Player author, String description) {
		this.name = name;
		this.author = author;
		this.description = description;
		this.questions = new ArrayList<Question>();
		this.state = SurveyState.unstarted;
	}
	
	  /***********/
	 /* SETTERS */
	/***********/
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setState(SurveyState state) {
		this.state = state;
	}
	
	  /***********/
	 /* GETTERS */
	/***********/
	
	public String getName() {
		return this.name;
	}
	
	public Player getAuthor() {
		return this.author;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public ArrayList<Question> getQuestions() {
		return this.questions;
	}
	
	public SurveyState getState() {
		return this.state;
	}

	  /************/
	 /* COMMANDS */
	/************/

	public void vote(Player sender, int question_number, int answer_number) {
		if(this.state == SurveyState.unstarted)										//Si le sondage n'as pas commence
			throw new zSurveyException(zSurveyException.Reason.UNSTARTED);			//Exception
		if(this.state == SurveyState.finished)										//Si le sondage est termine
			throw new zSurveyException(zSurveyException.Reason.FINISHED);			//Exception
		if(question_number < 1 || this.questions.size() < question_number)			//Si le numero de question n'existe pas
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_QUESTION);	//Exception
		
		this.questions.get(question_number-1).vote(sender, answer_number);
		sender.sendMessage(I.t("{darkgreen}Question {0} {darkgreen}: You have vote for the answer {1}",question_number, Character.toString((char)('A' + answer_number-1))));
	}
	
	public void addQuestion(Player sender, String question) {
		if(!this.author.equals(sender) && !Permissions.ADMIN.grantedTo(sender))	//Si la personne n'est pas l'auteur du sondage et quelle n'est pas admin
			throw new zSurveyException(zSurveyException.Reason.NON_AUTHOR);		//Exception
		if(this.questions.size() >= Config.MAX_QUESTIONS.get())					//Si le nombre max de questions est atteint
			throw new zSurveyException(zSurveyException.Reason.MAX_QUESTIONS);	//Exception

		this.questions.add(new Question(question));
		this.questions.get(this.questions.size()-1).see(sender, false, this.name, this.questions.size());
		this.resetVotes();
		this.state = SurveyState.unstarted;
	}
	
	public void addAnswer(Player sender, int question_number, String answer) {
		if(!this.author.equals(sender) && !Permissions.ADMIN.grantedTo(sender))	//Si la personne n'est pas l'auteur du sondage et quelle n'est pas admin
			throw new zSurveyException(zSurveyException.Reason.NON_AUTHOR);			//Exception
		if(question_number < 1 || this.questions.size() < question_number)			//Si le numero de question n'existe pas
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_QUESTION);	//Exception

		this.questions.get(question_number-1).addAnswer(sender, answer);
		this.questions.get(question_number-1).see(sender, false, this.name, question_number);
		this.resetVotes();
		this.state = SurveyState.unstarted;
	}
	
	public void start(Player sender) {
		if(!this.author.equals(sender) && !Permissions.ADMIN.grantedTo(sender))	//Si la personne n'est pas l'auteur du sondage et quelle n'est pas admin
			throw new zSurveyException(zSurveyException.Reason.NON_AUTHOR);			//Exception
		if(this.questions.size() == 0)												//Si le sondage ne possède pas de question
			throw new zSurveyException(zSurveyException.Reason.MIN_QUESTIONS);		//Exception
		for(Question question:questions) {
			if(question.getAnswers().size() < 2)									//Si une des questions ne possede pas plus de 2 reponses
				throw new zSurveyException(zSurveyException.Reason.MIN_ANSWERS);	//Exception
		}
		if(this.state == SurveyState.in_progress)									//Si le sondage est en cours
			throw new zSurveyException(zSurveyException.Reason.IN_PROGRESS);		//Exception
		
		for(Question question:this.questions)	//Pour chaque question du sondage
			question.resetVotes();				//Remet a zero les votes
		
		this.state = SurveyState.in_progress;
		RawMessage.broadcast(new RawText(I.t("{darkgreen}{0} just opened the survey \"{1}\". {gold}Click here to vote.", this.author.getName(), this.name, this.name)).command(SeeCommand.class,this.name));
	}

	public void see(Player sender) {
		
		if(this.state == SurveyState.unstarted && !sender.equals(this.author))	//Si le sondage n'est pas ouvert et si la personne n'est pas l'auteur
			throw new zSurveyException(zSurveyException.Reason.UNSTARTED);		//Exception

		sender.sendMessage(this.toString() + I.t(" {gold}Click on the answers to vote."));	
		for(int i = 0; i < this.questions.size(); i++) {
			if(this.state == SurveyState.finished)
				this.questions.get(i).see(sender, true, this.name, i+1);
			else
				this.questions.get(i).see(sender, false, this.name, i+1);
		}
	}
	
	public void end(Player sender) {
		if(!this.author.equals(sender) && !Permissions.ADMIN.grantedTo(sender))	//Si la personne n'est pas l'auteur du sondage et quelle n'est pas admin
			throw new zSurveyException(zSurveyException.Reason.NON_AUTHOR);			//Exception
		if(this.state == SurveyState.unstarted)										//Si le sondage est en cours
			throw new zSurveyException(zSurveyException.Reason.UNSTARTED);			//Exception
		
		this.state = SurveyState.finished;
		RawMessage.broadcast(new RawText(I.t("{darkgreen}{0} just closed the survey \"{1}\". {gold}Click here for results.", this.author.getName(), this.name, this.name)).command(SeeCommand.class,this.name));
	}
	
	public void removeQuestion(Player sender, int question_number) {
		if(!this.author.equals(sender) && !Permissions.ADMIN.grantedTo(sender))	//Si la personne n'est pas l'auteur du sondage et quelle n'est pas admin
			throw new zSurveyException(zSurveyException.Reason.NON_AUTHOR);			//Exception
		if(question_number < 1 || this.questions.size() < question_number)			//Si le numero de question n'existe pas
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_QUESTION);	//Exception

		this.questions.remove(question_number-1);
		sender.sendMessage(I.t("{darkgreen}Question {0} - Have been removed from the survey '{1}'",question_number,name));
	}
	
	public void removeAnswer(Player sender, int question_number, int answer_number) {
		if(this.state == SurveyState.in_progress)									//Si le sondage n'as pas commence
			throw new zSurveyException(zSurveyException.Reason.IN_PROGRESS);		//Exception
		if(question_number < 1 || this.questions.size() < question_number)			//Si le numero de question n'existe pas
			throw new zSurveyException(zSurveyException.Reason.UNKNOW_QUESTION);	//Exception
		
		this.questions.get(question_number-1).removeAnswer(sender, answer_number);
		sender.sendMessage(I.t("{darkgreen}Answer {0} : Have been removed from question {1}",Character.toString((char)('A' + answer_number-1)) ,question_number));
	}
	
	  /************/
	 /* METHODES */
	/************/
	
	public void resetVotes() {
		for(Question question:this.questions)
			question.resetVotes();
	}
	
	  /************/
	 /* TOSTRING */
	/************/
	
	public String toString() {
		
		String message = I.t("{darkgreen}{bold}{0}" , this.name);
		
		if(this.state == SurveyState.unstarted)
			message += I.t(" {red}[unstarted]");
		else if(this.state == SurveyState.in_progress)
			message += I.t(" {gold}[in progress]");
		else
			message += I.t(" {gray}[finished]");
		
		message += I.t("{gray}{italic} - {0} - {darkgreen}{1} {gray}({2})", this.author.getName(), this.description, this.questions.size());
		
		return message;
	}
	
	public RawText toRawText() {
		return new RawText(this.toString()).command(SeeCommand.class, this.name);
	}
}
