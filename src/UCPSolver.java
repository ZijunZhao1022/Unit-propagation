import java.FileInputStream;
import java.FileNotFoundException;
import java.FileOutputStream;
import java.OutputStream;
import java.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class UCPSolver {

	private Cnf cnf;
	private ArrayList<Literal> units;

	public UCPSolver() {
		this.cnf = new Cnf();
		this.units = new ArrayList<Literal>();
	}

	public UCPSolver(Cnf cnf) {
		this.cnf = cnf;
		this.units = new ArrayList<Literal>();
		getUnits();
	}

	private void getUnits() {
		ArrayList<Clause> c = this.cnf.getClauses();
		for (int i = 0; i < c.size(); ++i) {
			ArrayList<Literal> l = c.get(i).getLiterals();
			if (l.size() == 1) {
				units.add(l.get(0));
			}
		}
	}

	public boolean simpleSolver() {
		while (!units.isEmpty()) {
			Literal l = units.get(0);
			for (Clause c : cnf.getClauses()) {
				c.removeUnit(l);
			}
			units.remove(0);
			getUnits();
		}

		boolean sat = true;
		for (Clause c : cnf.getClauses()) {
			if (!c.getLiterals().isEmpty())
				sat = false;
		}

		return sat;
	}

	public void simpleSolver(String output) {
		String pu = "c";
		while (!units.isEmpty()) {
			Literal l = units.get(0);
			for (Clause c : cnf.getClauses()) {
				c.removeUnit(l);
			}
			pu += " " + l.toString();
			units.remove(0);
			getUnits();
		}

		boolean sat = true;
		for (Iterator<Clause> itr = cnf.getClauses().iterator(); itr.hasNext();) {
			Clause c = itr.next();
			if (!c.getLiterals().isEmpty())
				sat = false;
			else
				itr.remove();
		}

		try {
			OutputStream f = new FileOutputStream(output);
			OutputStreamWriter writer = new OutputStreamWriter(f);
			if (sat)
				writer.write("c Found satisfiable.\n");
			else
				writer.write("c Found unsatisfiable.\n");
			writer.write("c Propagated units:\n");
			writer.write(pu + "\n");
			writer.write("p cnf " + cnf.getSymbols().size() + " " + cnf.getClauses().size() + "\n");
			writer.write(cnf.printClauses());
			writer.close();
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws FileNotFoundException {
		if(args.length != 2)
		{
			System.err.println("Invalid arguments!\nUsage: \n\t java UCPSolver inputfile outputfile\n");
			return;
		}
		FileInputStream fis = new FileInputStream(args[0]);
		System.out.println("Read input file: "+args[0]);
		Cnf c = new Cnf(fis);
		UCPSolver s = new UCPSolver(c);
		s.simpleSolver(args[1]);
		System.out.println("Done!\nPlease check the output file: "+args[1]);
	}
}
