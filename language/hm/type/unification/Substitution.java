package language.hm.type.unification;

import language.hm.type.*;

import java.util.HashMap;

public class Substitution {
  private HashMap<Unifier.UVar,Type> subs;

  public Substitution() {
    this.subs = new HashMap<>();
  }

  public String toString() {
    return "Substitution(" + this.subs.toString() + ")";
  }

  public void insert(Unifier.UVar var, Type target) {
    this.subs.put(var, target);
    for (Unifier.UVar v : this.subs.keySet()) {
      if (this.subs.get(v).equals(var)) {
        this.subs.put(v, target);
      }
    }
  }

  public void merge(Substitution to) {
    for (Unifier.UVar v : this.subs.keySet()) {
      to.insert(v, this.subs.get(v));
    }
  }

  public Type apply(Type target) {
    Type t = target;
    for (Unifier.UVar var : this.subs.keySet()) {
      t = t.substitute(var, this.subs.get(var));
    }
    return t;
  }
}
