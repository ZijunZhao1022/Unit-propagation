

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
	}

	private boolean getUnits() {
		ArrayList<Clause> c = this.cnf.getClauses();
		for (int i = 0; i < c.size(); ++i) {
			ArrayList<Literal> l = c.get(i).getLiterals();
			if (l.size() == 1) {
				int res = noContradiction(l.get(0));
				if (res<0) {
					return false;
				}
				if (res>0) units.add(l.get(0));
			}
		}
		return true;
	}

	private int noContradiction(Literal l) {
		for (Literal u : units) {
			if (!u.equals(l))
				continue;
			else if (u.sign != l.sign) // If contradiction found
				return -1;
			else return 0;
		}
		return 1;
	}

	public void simpleSolver(String output) throws IOException {

		// If contradiction found in initial CNFs
		if (!getUnits()) {
			resultPrint(output, -1);
			return;
		}


		int uindex = 0;
		while (!units.isEmpty() && uindex < units.size()) {
			Literal l = units.get(uindex);
			// System.out.println("unit " + l.toString());
			for (Clause c : cnf.getClauses()) {
				// If contradiction found when simplifying CNFs
				if (!c.removeUnit(l)) {
					resultPrint(output, -1);
					return;
				}
			}

			// If contradiction found in simplified CNFs
			if (!getUnits()) {
				resultPrint(output, -1);
				return;
			}

			++uindex;
		}

		for (Iterator<Clause> itr = cnf.getClauses().iterator(); itr.hasNext();) {
			Clause c = itr.next();
			if (!c.getLiterals().isEmpty()) {
				resultPrint(output, 0);
				return;
			}
		}

		resultPrint(output, 1);

	}

	private void resultPrint(String output, int sat) throws IOException {
		// Remove empty clauses
		for (Iterator<Clause> itr = cnf.getClauses().iterator(); itr.hasNext();) {
			if (itr.next().getLiterals().isEmpty())
				itr.remove();
		}

		if (output.isEmpty()) {
			// Print header
			if (sat > 0)
				System.out.print("c Found satisfiable.\n");
			else if (sat < 0)
				System.out.print("c Found unsatisfiable.\n");
			else
				System.out.print("c Found undetermined.\n");

			// Print propagated units
			System.out.print("c Propagated units:\n");
			String pu = "c";
			for (Literal u : units)
				pu += " " + u;
			System.out.print(pu + "\n");

			System.out.print("p cnf " + cnf.getSymbols().size() + " " + cnf.getClauses().size() + "\n");
			System.out.print(cnf.printClauses());
		} else {
			OutputStream f = new FileOutputStream(output);
			OutputStreamWriter writer = new OutputStreamWriter(f);

			// Print header
			if (sat > 0)
				writer.write("c Found satisfiable.\r\n");
			else if (sat < 0)
				writer.write("c Found unsatisfiable.\r\n");
			else
				writer.write("c Found undetermined.\r\n");

			// Print propagated units
			writer.write("c Propagated units:\r\n");
			String pu = "c";
			for (Literal u : units)
				pu += " " + u;
			writer.write(pu + "\r\n");

			writer.write("p cnf " + cnf.getSymbols().size() + " " + cnf.getClauses().size() + "\r\n");
			writer.write(cnf.printClauses());
			writer.close();
			f.close();
		}
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.err.println("Invalid arguments!\nUsage: \n\t java UCPSolver inputfile (outputfile)\n");
			return;
		}
		FileInputStream fis = new FileInputStream(args[0]);
		System.out.println("Read input file: " + args[0]);
		Cnf c = new Cnf(fis);
		UCPSolver s = new UCPSolver(c);
		if (args.length == 2) {
			s.simpleSolver(args[1]);
			System.out.println("Done!\nPlease check the output file: " + args[1]);
		} else {
			s.simpleSolver("");
			System.out.println("Done!");
		}
	}
}

