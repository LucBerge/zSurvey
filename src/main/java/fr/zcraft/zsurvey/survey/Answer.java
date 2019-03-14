package fr.zcraft.zsurvey.survey;

import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.components.rawtext.RawText;
import fr.zcraft.zlib.tools.text.RawMessage;
import fr.zcraft.zsurvey.commands.VoteCommand;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Answer {

	private String answer;
	private ArrayList<Player> voters;
	
	public Answer(String answer) {
		this.answer = answer;
		this.voters = new ArrayList<Player>();
	}
	
	  /***********/
	 /* SETTERS */
	/***********/
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	  /***********/
	 /* GETTERS */
	/***********/
	
	public String getAnswer() {
		return this.answer;
	}
	
	public ArrayList<Player> getVoters() {
		return this.voters;
	}
	
	  /************/
	 /* COMMANDS */
	/************/
	
	public void see(Player sender, boolean result, String name, int question_number, int answer_number, float statistics) {
		
		String message = "";
		if (voters.contains(sender))
			message =  I.t("      {red}{0} - {1}", Character.toString((char)('A' + answer_number -1)), this.answer);
		else
			message =  I.t("      {gray}{0} - {1}", Character.toString((char)('A' + answer_number -1)), this.answer);

		if (result == true)
			message += I.t(" ({0}%)", statistics);

		RawMessage.send(sender, new RawText(message).command(VoteCommand.class, name + " " + question_number + " " + answer_number));
	}
	
	  /************/
	 /* METHODES */
	/************/
	
	public void vote(Player sender) {
		this.voters.add(sender);
	}
	
	public void cancelvote(Player sender) {
		this.voters.remove(sender);
	}
}
