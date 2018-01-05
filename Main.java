import language.hm.*;
import language.hm.type.*;
import language.hm.type.unification.*;

class Main {
    public static void main(String[] args) throws UnificationError, TypeError {
        Expr id = new Expr.Abs("x", new Expr.Var("x"));
        Expr id_id= new Expr.App(id, id);
        Expr id_id_id = new Expr.App(id, id_id);
        System.out.println(id_id_id.infer(new Context(new Unifier())).generalize());
    
        Expr y_comb = new Expr.Abs(
                                   "f",
                                   new Expr.App(
                                                new Expr.Abs("x", new Expr.App(new Expr.Var("f"), new Expr.App(new Expr.Var("x"), new Expr.Var("x")))),
                                                new Expr.Abs("x", new Expr.App(new Expr.Var("f"), new Expr.App(new Expr.Var("x"), new Expr.Var("x"))))
                                                ));
        System.out.println(y_comb.infer(new Context(new Unifier())).generalize());
    }
}
