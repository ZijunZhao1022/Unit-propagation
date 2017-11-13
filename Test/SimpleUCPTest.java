import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

public class SimpleUCPTest {
	String testStr = "c  simple.cnf\r\n" + "c\r\n" + "p cnf 4 4\r\n" + "1 2 0\r\n" + "-1 3 0\r\n" + "-3 4 0\r\n"
			+ "1 0";
	
	@Test
	public void test() {
		Formula bf = new Formula();
		CNFParser cp = new CNFParser(bf);
		InputStream is = new ByteArrayInputStream(testStr.getBytes());
		cp.parse(is);
		
		SimpleUCP s = new SimpleUCP(bf);
		assertTrue(s.run() == 1);
		
	}

}
