package net.mms_projects.irc.channel_bots.pbl;

public class ProgrammableBotsLanguage {

	private Parser parser = new Parser();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ProgrammableBotsLanguage();
	}
	
	public ProgrammableBotsLanguage() {
		Parser parser = new Parser();
		
		this.parse("$identifier(argument1,argument2)");
		this.parse("$replace(Kijk een mooie tekst, mooie, lelijke)");
		this.parse("$identifier(argument1,argument2,argument3)");
		this.parse("$identifier(argument1, argument2)");
		this.parse("$identifier(argument1, argument2,argument3)");
		this.parse("Het is $time");
		this.parse("Het is $time of niet?");
		this.parse("Hai :) $identifier(argument1, argument2,argument3) Hoe is het");
	}
	
	public void parse(String rawdata) {
		String input = rawdata;
		String output = this.parser.parse(input);
		System.out.println("Input: " + input);
		System.out.println("Output: " + output);
		System.out.println("----------");
	}
}
