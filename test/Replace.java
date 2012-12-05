

import static org.junit.Assert.*;

import net.mms_projects.irc.channel_bots.pbl.Handler;
import net.mms_projects.irc.channel_bots.pbl.Parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Replace {

	private Parser parser;
	
	@Before
	public void setUp() throws Exception {
		Handler handler = new Handler();
		this.parser = new Parser(handler);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRun() {
		assertEquals("Leuke value?", parser.eval("$replace(%internal.testvariable, Test, Leuke)"));
	}

}
