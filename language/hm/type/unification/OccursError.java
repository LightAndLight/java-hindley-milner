package language.hm.type.unification;

import language.hm.type.*;

public class OccursError extends UnificationError {
    private Type a;
    private Type b;
  
    public OccursError(Type a, Type b) {
        this.a = a;
        this.b = b;
    }
  
    public String getMessage() {
        return "Cannot create infinite type " + this.a.toString() + " = " + this.b.toString();
    }
}
