import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

public class FormulaTest {
	
	String exp_vs[] = {"1", "2", "3", "4"};
	String exp_cnf[] = {"1||2","!1||3","!3||4","1"};
	Boolean exp_res[] = {false, false, false, true};
	String expStr = "( 1||2 ) && ( !1||3 ) && ( !3||4 ) && ( 1 )";
	
	@Test
	public void test() {
		Formula f = new Formula();
		f.setVariables(new ArrayList<String>(Arrays.asList(exp_vs)));
		f.setCNFS(new ArrayList<String>(Arrays.asList(exp_cnf)));
		HashMap<String, Boolean> hm = new HashMap<String, Boolean>();
		for(int i=0; i<exp_vs.length;++i)
			hm.put(exp_vs[i], exp_res[i]);
		f.setVmap(hm);
		
		ArrayList<String> v = f.getVariables();
		ArrayList<String> cnfs = f.getCNFS();
		HashMap<String, Boolean> rhm = f.getVmap();
		assertTrue(Arrays.equals(v.toArray(new String[v.size()]), exp_vs));
		assertTrue(Arrays.equals(cnfs.toArray(new String[cnfs.size()]), exp_cnf));
		assertTrue(Arrays.equals(rhm.values().toArray(), exp_res));

		assertEquals(expStr, f.print());
	}
}
