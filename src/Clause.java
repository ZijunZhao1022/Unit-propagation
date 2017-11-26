import java.util.ArrayList;

public class Clause {
             public Clause() {
                     this.literals = new ArrayList<Literal>(); 
             }
             
             public Clause(ArrayList<Literal> literals) {
                     this.literals = literals;

             }
             public void addLiteral(int symbol, boolean sign) {
                     literals.add(new Literal (symbol, sign))
             } 
             public void addLiteral(int symbol) {
                     if (symbol < 0)
                     { 
                             literals.add( new Literal(-symbol,false));                                   
                     }
                     else
                             literals.add(new Literal(symbol, true));
             }
             public boolean hasLiteral(Literal l) {
		return literals.contains(l);
	     }

             public void removeUnit(Literal unit) {
		int size = literals.size();
		int index = -1;
		for(int i=0;i<size;++i) {
			if(literals.get(i).equals(unit))
				index = i;
		}
		if(index > -1)
		{
			if(unit.sign != literals.get(index).sign)
				literals.remove(index);
			else
				literals.clear();
		}
	}



}