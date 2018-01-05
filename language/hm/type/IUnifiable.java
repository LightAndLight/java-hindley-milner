package language.hm.type;

import language.hm.type.unification.*;

public interface IUnifiable {
    Substitution unify(Unifier.UVar b) throws UnificationError;
    Substitution unify(Type.TypeVar b) throws UnificationError;
    Substitution unify(Type.FunctionType b) throws UnificationError;
    Substitution unify(Type b) throws UnificationError;
    boolean occurs(Unifier.UVar b);
}
