package language.hm.type.unification;

import language.hm.type.Type;

public interface IUnifier {
    public void unify(Type a, Type b) throws UnificationError;
    public Unifier.UVar fresh();
    public Type applySubstitutions(Type target);
}
