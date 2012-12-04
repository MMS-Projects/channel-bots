package net.mms_projects.irc.channel_bots.pbl;

public class ProgrammableBotsLanguage {

	private Parser parser = new Parser(new Handler());;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ProgrammableBotsLanguage();
	}
	
	public ProgrammableBotsLanguage() {
		this.parse("START $a(a,(a,b)) END");
		this.parse("START Het is $time END");
		this.parse("START Het is $time of niet? END");
		this.parse("START $identifier(argument1,argument2).property END");
		this.parse("START $replace(Kijk een mooie tekst, mooie, lelijke) END");
		this.parse("START $identifier(argument1,argument2,argument3) END");
		this.parse("START $identifier(argument1, argument2) END");
		this.parse("START $identifier(argument1, argument2,argument3) END");
		
		this.parse("START Hai :) $identifier(argument1, argument2,argument3) Hoe is het END");
		
		this.parse("START $chr($calc(10+10)) END");
	}
	
	public void parse(String rawdata) {
		String input = rawdata;
		String output = this.parser.parse(input);
		
	}
}
