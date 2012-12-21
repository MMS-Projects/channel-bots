import static org.junit.Assert.*;

import net.mms_projects.irc.channel_bots.pbl.Handler;
import net.mms_projects.irc.channel_bots.pbl.Parser;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PblParserAppend {

	private static Parser parser;
	
	@BeforeClass
	public static void testSetup() {
		Handler handler = new Handler();
		handler.setVariable("internal.irc.nick", "testnick");
		 
		
		parser = new Parser(handler);
	}

	@AfterClass
	public static void testCleanup() {
		parser = null;
	}

	@Test
	public void testParse() {
		parseTest("$time", "$chr(36) $+ time");
		parseTest("$nick", "$chr(36) $+ nick");
		parseTest("testnick", "$eval($chr(36) $+ nick)");
		
	}
	
	private void parseTest(String expected, String input) {
		assertEquals(expected, parser.eval(input));
	}

}
