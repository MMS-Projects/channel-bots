import static org.junit.Assert.*;

import net.mms_projects.irc.channel_bots.pbl.Handler;
import net.mms_projects.irc.channel_bots.pbl.Parser;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PblParserTest {

	private static Parser parser;
	
	@BeforeClass
	public static void testSetup() {
		Handler handler = new Handler();
		parser = new Parser(handler);
	}

	@AfterClass
	public static void testCleanup() {
		parser = null;
	}

	@Test
	public void testParse() {
		parseTest("test2", "$replace(test1, test1, test2)");
		parseTest("65", "$asc(A)");
		parseTest("A", "$chr(65)");
		parseTest("65", "$asc($replace(B, B, A))");
		parseTest("Test value?", "%internal.testvariable");
		parseTest("Leuke value?", "$replace(%internal.testvariable, Test, Leuke)");
		parseTest("$time", "$+($chr(36), time)");
		parseTest("Test value?", "$eval($+(%%, internal.testvariable), 2)");
	}
	
	private void parseTest(String expected, String input) {
		assertEquals(expected, parser.eval(input));
	}

}
