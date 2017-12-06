import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Cnf {

	private ArrayList<Integer> symbols;
	public ArrayList<Integer> getSymbols() {
		return symbols;
	}

	private ArrayList<Clause> clauses;

	public Cnf() {
		this.clauses = new ArrayList<Clause>();
		this.symbols = new ArrayList<Integer>();
	}

	public Cnf(InputStream fstream) {
		this.clauses = new ArrayList<Clause>();
		this.symbols = new ArrayList<Integer>();
		this.parse(fstream);
	}
	
	public Cnf(int numSymbols) {
		this.clauses = new ArrayList<Clause>();
		this.symbols = new ArrayList<Integer>();
		for (int i = 1; i <= numSymbols; ++i) {
			symbols.add(i);
		}
	}
        /**
	 * Adds a new clause to this CNF.
	 */
	public void addClause(ArrayList<Literal> l) {
		clauses.add(new Clause(l));
	}

	public ArrayList<Clause> getClauses() {
		return clauses;
	}

	
	public String printClauses() {
		String s="";
		for (Clause c : this.clauses) {
			s+=c.print()+'\n';
		}
		return s;
	}
	public void parse(InputStream fstream) {
		int numVariables = 0;
		int numClauses = 0;

	try {
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			int index = 0;
	                while ((line = br.readLine()) != null && line.length() > 0) {
				if (line.length() == 0)
					continue;
				if (line.charAt(0) != 'c') {
					if (line.charAt(0) == 'p') {
						String[] content = line.split(" ");
						numVariables = Integer.parseInt(content[2]);
						numClauses = Integer.parseInt(content[3]);

						for (int i = 1; i <= numVariables; ++i)
							symbols.add(i);
						for (int i = 0; i < numClauses; ++i)
							clauses.add(new Clause());
					} else {
						// String f = line.substring(0, line.length() - 2).trim();
						String f = line.replaceAll("\\s+", " ");
						String[] content = f.split(" ");
						// System.out.println("length" + content.length);
						for (String s : content) {
							if (!s.equals("0")) {
								clauses.get(index).addLiteral(Integer.parseInt(s));
							} else {
								index += 1;
							}
						}
					}

				}

			}
			// printClauses();

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
