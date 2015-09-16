package Interfaces.Misc.Spell;


import java.util.List;

public interface KeyI<T>
{
    public List<T> getKeys();
    public void addKey(T key);
    public void removeKey(T key);
}
