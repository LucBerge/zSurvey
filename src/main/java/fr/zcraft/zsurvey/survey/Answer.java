package fr.zcraft.zsurvey.survey;

import fr.zcraft.zlib.components.i18n.I;

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
	 /* METHODES */
	/************/
	
	public void vote(Player player) {
		this.voters.add(player);
	}
	
	public void cancelvote(Player sender) {
		this.voters.remove(sender);
	}
	
	  /************/
	 /* TOSTRING */
	/************/
	
	public String toString(Player sender) {
		if (voters.contains(sender))
			return  I.t("{gray}{bold}{0}", this.answer);
		else
			return  I.t("{gray}{0}", this.answer);
	}
}
