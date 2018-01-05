package language.hm.type.unification;

import language.hm.type.Type;
import language.hm.type.Generalizer;

public class Unifier implements IUnifier {
  public static class UVar extends Type {
    private int id;
    
    public UVar(int id) {
      this.id = id;
    }
    
    public int hashCode() {
      return this.id * 13 + 7;
    }
    
    public boolean equals(Object o) {
      if (o instanceof UVar) {
        UVar u = (UVar)o;
        return this.id == u.id;
      } else {
        return false;
      }
    }
    
    public String toString() {
      return "UVar(" + Integer.toString(this.id) + ")";
    }
      
    public Type runGeneralize(Generalizer g) {
      return g.generalize(this);
    }
    
    public Substitution unify(UVar b) {
      Substitution s = new Substitution();
      if (this.equals(b)) {
        return s;
      } else {
        s.insert(b, this);
        return s;
      }
    }
    
    public Substitution unify(Type.TypeVar b) throws UnificationError {
      Substitution s = new Substitution();
      s.insert(this, b);
      return s;
    }
    
    public Substitution unify(Type.FunctionType b) throws UnificationError {
      if (b.occurs(this)) {
        throw new OccursError(this, b);
      }

      Substitution s = new Substitution();
      s.insert(this, b);
      return s;
    }
    
    public Substitution unify(Type b) throws UnificationError {
      if (b.occurs(this)) {
        throw new OccursError(this, b);
      }

      Substitution s = new Substitution();
      s.insert(this, b);
      return s;
    }
    
    public Type substitute(UVar v, Type t) {
      if (v.equals(this)) {
        return t;
      } else {
        return this;
      }
    }
    
    public boolean occurs(Unifier.UVar v) {
      return this.equals(v);
    }
  }
  
  private Substitution substitutions;
  private int freshCount;
  
  public Unifier() {
    this.freshCount = 0;
    this.substitutions = new Substitution();
  }
  
  public String toString() {
    return "Unfier(" + Integer.toString(this.freshCount) + "," + this.substitutions.toString() + ")";
  }

  public void unify(Type a, Type b) throws UnificationError {
    Substitution s = a.unify(b);
    s.merge(this.substitutions);
  }
  
  public UVar fresh() {
    this.freshCount++;
    return new UVar(freshCount);
  }
  
  public Type applySubstitutions(Type target) {
    return this.substitutions.apply(target);
  }
}
