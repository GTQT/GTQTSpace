package meowmel.gtqtspace.api.unifications.materials;

import gregtech.api.unification.material.properties.OreProperty;
import gregtech.api.unification.material.properties.PropertyKey;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Duranium;
import static meowmel.gtqtspace.api.unifications.GTQTSMaterialFlags.GENERATE_DENSE_PLATE;
import static meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials.*;
import static meowmel.gtqtspace.api.unifications.GTQTSpaceMaterials.Desh;

public class MaterialPropertyAddition {
    public static void init()
    {

        OreProperty oreProp;

        oreProp = MeteoricIron.getProperty(PropertyKey.ORE);
        oreProp.addOreByProducts(Iron, Bauxite, Silver);

        oreProp = Desh.getProperty(PropertyKey.ORE);
        oreProp.addOreByProducts(Uranium, Monazite, Thorium);

        oreProp = Columbite.getProperty(PropertyKey.ORE);
        oreProp.addOreByProducts(Iron, Manganese, Niobium);

        oreProp = Gallite.getProperty(PropertyKey.ORE);
        oreProp.setOreByProducts(Sulfur,Copper,Copper,Gallium);

        //添加超致密板
        StainlessSteel.addFlags(GENERATE_DENSE_PLATE);
        Titanium.addFlags(GENERATE_DENSE_PLATE);
        TungstenSteel.addFlags(GENERATE_DENSE_PLATE);
        NiobiumTitanium.addFlags(GENERATE_DENSE_PLATE);
        RhodiumPlatedPalladium.addFlags(GENERATE_DENSE_PLATE);
        Naquadah.addFlags(GENERATE_DENSE_PLATE);
        Duranium.addFlags(GENERATE_DENSE_PLATE);

        MeteoricIron.addFlags(GENERATE_DENSE_PLATE);
        Desh.addFlags(GENERATE_DENSE_PLATE);
    }
}
