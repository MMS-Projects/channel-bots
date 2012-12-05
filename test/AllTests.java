import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ PblParserTest.class, Replace.class, PblParserAppend.class })
public class AllTests {

}
