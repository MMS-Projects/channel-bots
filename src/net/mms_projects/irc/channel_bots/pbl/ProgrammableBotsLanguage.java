package net.mms_projects.irc.channel_bots.pbl;

public class ProgrammableBotsLanguage {

	private Parser parser;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ProgrammableBotsLanguage();
	}
	
	public ProgrammableBotsLanguage() {	
		Handler handler = new Handler();
		handler.setVariable("internal.irc.nick", "Awesome-User");
		handler.setVariable("internal.irc.chan", "#test");
		handler.setVariable("internal.irc.me", "PBL-TestBot");
		
		this.parser = new Parser(handler);
		this.parse("$a(a,(a,b))");
		this.parse("Het is $time");
		this.parse("Het is $time of niet?");
		this.parse("$identifier(argument1,argument2).property");
		this.parse("$replace(Kijk een mooie tekst, mooie, lelijke)");
		this.parse("$identifier(argument1,argument2,argument3)");
		this.parse("$identifier(argument1, argument2)");
		this.parse("$identifier(argument1, argument2,argument3)");
		
		this.parse("Hai :) $identifier(argument1, argument2,argument3) Hoe is het");
		
		this.parse("$chr($calc(10+10))");
		
		this.parse("%internal.testvariable");
	}
	
	public void parse(String rawdata) {
		String input = "START " + rawdata + " END";
		String output = this.parser.eval(input);
		
	}
}
