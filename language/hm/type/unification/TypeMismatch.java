package language.hm.type.unification;

import language.hm.type.Type;

public class TypeMismatch extends UnificationError {
    private Type a;
    private Type b;

    public TypeMismatch(Type a, Type b) {
        this.a = a;
        this.b = b;
    }
  
    public String getMessage() {
        return "Cannot unify " + this.a.toString() + " with " + this.b.toString();
    }
}
