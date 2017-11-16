import java.util.ArrayList;
import java.util.HashMap;

public class Formula {
        ArrayList<String> variables;
        ArrayList<String> CNFS;
        HashMap<String, Boolean> vmap;
        
        public Formula() {
                this.variables = new ArrayList<String>();
                this.CNFS = new ArrayList<String>();
                this.vmap = new HashMap<String, Boolean>();
        }
        
        public ArrayList<String> getVariables() {
                return variables;
        }

        public void setVariables(ArrayList<String> variables) {
                this.variables = variables;
        }

        public ArrayList<String> getCNFS() {
                return CNFS;
        }

        public void setCNFS(ArrayList<String> cnfs) {
                this.CNFS = cnfs;
        }

        public HashMap<String, Boolean> getVmap() {
                return vmap;
        }

        public void setVmap(HashMap<String, Boolean> vmap) {
                this.vmap = vmap;
        }
        
        public String print() {
		String s="";
                // System.out.println("Formula2: ");
                for (int i = 0; i < CNFS.size(); ++i) {
                        s += "( " + CNFS.get(i) + " )";
			// String s = CNFS.get(i);
                        // System.out.print("( " + s + " )");
                        if (i + 1 != CNFS.size())
				s += " && ";
                                //System.out.print(" && ");
                }
               // System.out.println("");
		return s;
        }
        
}
