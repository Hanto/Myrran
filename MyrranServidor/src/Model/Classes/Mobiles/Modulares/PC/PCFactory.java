package Model.Classes.Mobiles.Modulares.PC;// Created by Hanto on 11/08/2015.

import Model.Classes.Mobiles.Modulares.Abstractos.CasterPersonalizadoAbstract;
import Model.Mobiles.Propiedades.*;

public enum PCFactory
{
    NUEVOPC
    {
        @Override public PC nuevo(int connectionID)
        {
            return new PC(connectionID, 48, 48, new IdentificableBase(), new CasterBase(),
                    new CasterPersonalizadoAbstract(), new VulnerableBase(), new DebuffeableBase(),
                    new PCStatsBase(), new AnimableBase());
        }
    };
    public abstract PC nuevo (int connectionID);
}
