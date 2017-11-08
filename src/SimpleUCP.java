package io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SimpleUCP {

        // units stores the unit literal and its value
        Map<String, Boolean> units;
        // variables stores the name of all the variables
        ArrayList<String> variables;
        // cnfs stores all the CNFs
        ArrayList<String> cnfs;
        HashMap<String, Boolean> vmap;

        public SimpleUCP(Formula bf) {
                this.units = new HashMap<String, Boolean>();
                this.variables = bf.getVariables();
                this.vmap = bf.getVmap();
                this.cnfs = bf.getCNFS();
        }

        // Run the propagation
        public void trim() {
                for (int i=0;i<cnfs.size();++i) {
                        String s = cnfs.get(i);
                        for (String u : units.keySet()) {
                                // Deal with true unit literals
                                if (units.get(u) == true) {
                                        // System.out.println("u: "+u);
                                        // System.out.println("s: "+s);
                                        
                                        // Remove them from CNFS
                                        while (s.contains("!" + u + "||")) {
                                                s = s.replace("!" + u + "||", "");
                                        }
                                        while (s.contains("||" + "!" + u)) {
                                                s = s.replace("||" + "!" + u, "");
                                        }
                                        while (s.contains(u)) {
                                                s = "";
                                        }
                                }
                                // Deal with false unit literals
                                else {
                                        // Remove their negative from CNFS
                                        while (s.contains(u + "||")) {
                                                s = s.replace( u + "||", "");
                                        }
                                        while (s.contains("||" +  u)) {
                                                s = s.replace("||" +  u, "");
                                        }
                                        while (s.contains("!"+u)) {
                                                s = "";
                                        }
                                }
                        }
                        // System.out.println("sf: "+s);
                        // Update the CNF
                        cnfs.set(i, s);
                
                }
                
                 for (Iterator<String> itr = cnfs.iterator(); itr.hasNext();) {
                         String s = itr.next();
                                // System.out.println("content: "+ s);
                         // Remove empty CNFs
                         if (s == "") { itr.remove(); }
                         }
                

                        // System.out.println("no: "+ cnfs.size());
        }

        public int run() {
                int changes = 0;

                // Find units and store it
                for (String s : cnfs) {
                        // System.out.println(s);
                        String[] t = s.split("||");
                        if (t.length == 1) {
                                // System.out.println("t0 is " + t[0]);
                                if(t[0].startsWith("!")) units.put(t[0].substring(1),false);
                                else units.put(t[0], true);
                                changes++;
                                // System.out.println("change:" +changes);
                        }
                }

                // Start UCP
                while (changes != 0 && cnfs.size() > 0) {
                        trim();
                        // System.out.println("r");
                        changes = 0;
                        for (String s : cnfs) {
                                // System.out.println("2s:" + s);
                                String[] t = s.split("||");
                                if (t.length == 1) {
                                        // System.out.println("t0 is " + t[0]);
                                        if(t[0].startsWith("!")) units.put(t[0].substring(1),false);
                                        else units.put(t[0], true);
                                        changes++;
                                        // System.out.println("change:" +changes);
                                }
                        }
                }
                // System.out.println("un:" + units.size());
                // System.out.println("cnfs:" + cnfs.size());
                if(cnfs.size()==0) return 1;
                
                return 0;
        }

        public static void main(String[] args) {
                
                // Formula consists of CNFs
                Formula bf = new Formula();
                
                // Parser is used to translate CNF file into Formula object
                CNFParser cp = new CNFParser(bf);
                
                // The first argument is the file path, i.e., C:\sample.txt
                cp.parse(args[0]);
                
                // Print the parsed formula
                bf.print();

                // The simplest ucp solver
                SimpleUCP s = new SimpleUCP(bf);
                int r =  s.run();
                
                System.out.println("Result: ");
                System.out.println(r);
                
        }
        

}
