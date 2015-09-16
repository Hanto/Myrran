package Interfaces.Misc.Spell;


public interface LockI<T> extends KeyI<T>
{
    public boolean abreLaCerradura(KeyI<T> key);
}
