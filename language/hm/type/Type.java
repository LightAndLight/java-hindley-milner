package language.hm.type;

import language.hm.type.unification.*;

public abstract class Type {
  public Type generalize() {
    return this.runGeneralize(new Generalizer());
  }

  public abstract Substitution unify(Unifier.UVar b) throws UnificationError;
  public abstract Substitution unify(Type.TypeVar b) throws UnificationError;
  public abstract Substitution unify(Type.FunctionType b) throws UnificationError;
  public abstract Substitution unify(Type b) throws UnificationError;
  public abstract boolean occurs(Unifier.UVar b);
  public abstract Type substitute(Unifier.UVar v, Type t);
  public abstract Type runGeneralize(Generalizer g);

  public static class TypeVar extends Type {
    private String name;
    public TypeVar(String name) {
      this.name = name;
    }

    public int hashCode() {
      return this.name.hashCode();
    }

    public boolean equals(Object o) {
      if (o instanceof TypeVar) {
        TypeVar t = (TypeVar)o;
        return this.name.equals(t.name);
      } else {
        return false;
      }
    }

    public Type runGeneralize(Generalizer g) {
      return g.generalize(this);
    }

    public String toString() {
      return "TypeVar(" + this.name + ")";
    }

    public Substitution unify(Unifier.UVar b) {
      Substitution s = new Substitution();
      s.insert(b, this);
      return s;
    }

    public Substitution unify(Type.TypeVar b) throws UnificationError {
      throw new TypeMismatch(this, b);
    }

    public Substitution unify(Type.FunctionType b) throws UnificationError {
      throw new TypeMismatch(this, b);
    }

    public Substitution unify(Type b) throws UnificationError{
      return b.unify(this);
    }

    public Type substitute(Unifier.UVar v, Type t) {
      return this;
    }

    public boolean occurs(Unifier.UVar v) {
      return false;
    }
  }

  public static class FunctionType extends Type {
    private Type from;
    private Type to;
    public FunctionType(Type from, Type to) {
      this.from = from;
      this.to = to;
    }
    
    public int hashCode() {
      return this.from.hashCode() + this.to.hashCode();
    }
    
    public boolean equals(Object o) {
      if (o instanceof FunctionType) {
        FunctionType t = (FunctionType)o;
        return this.from.equals(t.from) && this.to.equals(t.to);
      } else {
        return false;
      }
    }
    
    public String toString() {
      return "FunctionType(" + this.from.toString() + "," + this.to.toString() + ")";
    }
    
    public Type runGeneralize(Generalizer g) {
      this.from = g.generalize(this.from);
      this.to = g.generalize(this.to);
      return this;
    }
    
    public Substitution unify(Unifier.UVar b) {
      Substitution s = new Substitution();
      s.insert(b, this);
      return s;
    }
    
    public Substitution unify(Type.TypeVar b) throws UnificationError {
      throw new TypeMismatch(this, b);
    }
    
    public Substitution unify(Type.FunctionType b) throws UnificationError {
      Substitution fromSubs = this.from.unify(b.from);
      Substitution toSubs = fromSubs.apply(this.to).unify(fromSubs.apply(b.to));
      toSubs.merge(fromSubs);
      return fromSubs;
    }
    
    public Substitution unify(Type b) throws UnificationError {
      return b.unify(this);
    }
    
    public Type substitute(Unifier.UVar v, Type t) {
      this.from = this.from.substitute(v, t);
      this.to = this.to.substitute(v, t);
      return this;
    }
    
    public boolean occurs(Unifier.UVar v) {
      return this.from.occurs(v) || this.to.occurs(v);
    }
  }
}
