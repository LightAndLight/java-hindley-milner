package language.hm;

import java.util.HashMap;
import java.util.LinkedList;

import language.hm.type.*;
import language.hm.type.unification.*;

public class Context {
  private HashMap<String,LinkedList<Type>> mapping;
  private IUnifier unifier;

  public Context(IUnifier unifier) {
    this.mapping = new HashMap<>();
    this.unifier = unifier;
  }
  
  public IUnifier getUnifier() {
    return this.unifier;
  }
  
  public String toString() {
    return "Context(" + this.unifier.toString() + "," + this.mapping.toString() + ")";
  }
  
  public Type lookup(String key) {
    LinkedList<Type> t = this.mapping.get(key);
    if (t != null) {
      return t.peekFirst();
    } else {
      return null;
    }
  }
  
  public void delete(String key) {
    LinkedList<Type> t = this.mapping.get(key);
    if (t != null) {
      t.removeFirst();
      if (t.isEmpty()) {
        this.mapping.remove(key);
      }
    }
  }
  
  public void insert(String key, Type value) {
    LinkedList<Type> t = this.mapping.get(key);
    if (t != null) {
      t.addFirst(value);
    } else {
      LinkedList<Type> l = new LinkedList<>();
      l.addFirst(value);
      this.mapping.put(key, l);
    }
  }

}
