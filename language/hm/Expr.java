package language.hm;

import language.hm.Context;
import language.hm.type.*;
import language.hm.type.unification.*;

public abstract class Expr {
  
  public abstract Type infer(Context context) throws UnificationError, TypeError;
  
  public static class Var extends Expr {
    private String name;
    public Var(String name) {
      this.name = name;
    }
    
    public Type infer(Context context) throws UnificationError, TypeError {
      Type ty = context.lookup(this.name);
      if (ty == null) {
        throw new TypeError.NotInContext(this.name);
      } else {
        return context.getUnifier().applySubstitutions(ty);
      }
    }
  }
  
  public static class Abs extends Expr {
    private String var;
    private Expr body;
    public Abs(String var, Expr body) {
      this.var = var;
      this.body = body;
    }
    
    public Type infer(Context context) throws UnificationError, TypeError {
      Unifier.UVar fromVar = context.getUnifier().fresh();
      context.insert(this.var, fromVar);
      Type retTy = body.infer(context);
      context.delete(this.var);
      Type ty = new Type.FunctionType(fromVar, retTy);
      return context.getUnifier().applySubstitutions(ty);
    }
  }
  
  public static class App extends Expr {
    private Expr f;
    private Expr x;
    public App(Expr f, Expr x) {
      this.f = f;
      this.x = x;
    }
    
    public Type infer(Context context) throws UnificationError, TypeError {
      Type xTy = this.x.infer(context);
      Type fTy = this.f.infer(context);
      Unifier.UVar retTy = context.getUnifier().fresh();
      context.getUnifier().unify(fTy, new Type.FunctionType(xTy, retTy));
      
      return context.getUnifier().applySubstitutions(retTy);
    }
  }
}
