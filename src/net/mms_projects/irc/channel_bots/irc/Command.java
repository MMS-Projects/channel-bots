package net.mms_projects.irc.channel_bots.irc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public abstract class Command {

	public Pattern inputPattern;
	public String outputPattern;
	
	public Command(String inputPattern, String outputPattern) {
		this.inputPattern = Pattern.compile(inputPattern);
		this.outputPattern = outputPattern;
	}
	
	public boolean checkMatch(String command) {
		Matcher matcher = this.inputPattern.matcher(command);
		return matcher.find();
	}
	
	public Matcher match(String rawdata) {
		return inputPattern.matcher(rawdata);
	}
	
	@Override
	public String toString() {
		return build();
	}
	
	abstract public void parse(String rawdata);
	abstract public String build();

}
