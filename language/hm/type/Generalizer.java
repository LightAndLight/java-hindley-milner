package language.hm.type;

import java.util.HashMap;

import language.hm.type.*;
import language.hm.type.unification.*;

public class Generalizer {
    private int varCount;
    private HashMap<Unifier.UVar,String> mapping;

    public Generalizer() {
        this.varCount = 0;
        this.mapping = new HashMap<>();
    }

    private void addEntry(Unifier.UVar u) {
        mapping.put(u, "t" + Integer.toString(this.varCount));
        varCount++;
    }

    public Type generalize(Unifier.UVar u) {
        String res = this.mapping.get(u);
        if (res == null) {
            this.addEntry(u);
            return new Type.TypeVar(this.mapping.get(u));
        } else {
            return new Type.TypeVar(res);
        }
    }

    public Type generalize(Type.TypeVar t) {
        return t;
    }

    public Type generalize(Type.FunctionType t) {
        return t.runGeneralize(this);
    }

    public Type generalize(Type t) {
        return t.runGeneralize(this);
    }
}
