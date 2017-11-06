/**
 * 
 */
package io;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

/**
 * @author
 *
 */
public class CNFParserTest {
	Formula f = new Formula();
	CNFParser cp = new CNFParser(f);
	String testStr = "c  simple.cnf\r\n" + "c\r\n" + "p cnf 4 4\r\n" + "1 2 0\r\n" + "-1 3 0\r\n" + "-3 4 0\r\n"
			+ "1 0";
	String expStr = "( 1||2 ) && ( !1||3 ) && ( !3||4 ) && ( 1 )";
	
	@Test
	public void test() {
		System.out.println("test CNF Parser");
		InputStream is = new ByteArrayInputStream(testStr.getBytes());
		cp.parse(is);
		assertEquals(expStr, cp.print());
	}

}
