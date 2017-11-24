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





}