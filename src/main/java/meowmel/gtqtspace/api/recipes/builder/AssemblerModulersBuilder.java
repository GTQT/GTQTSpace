package meowmel.gtqtspace.api.recipes.builder;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.properties.impl.ComputationProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import keqing.gtqtcore.api.utils.GTQTLog;
import meowmel.gtqtspace.api.recipes.properties.TierProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AssemblerModulersBuilder extends RecipeBuilder<AssemblerModulersBuilder> {

    public AssemblerModulersBuilder() {
    }

    public AssemblerModulersBuilder(Recipe recipe, RecipeMap<AssemblerModulersBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public AssemblerModulersBuilder(RecipeBuilder<AssemblerModulersBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public boolean applyPropertyCT(String key, Object value) {
        if (key.equals(TierProperty.KEY)) {
            this.tier(((Number) value).intValue());
            return true;
        }
        if (key.equals(ComputationProperty.KEY)) {
            this.CWUt(((Number) value).intValue());
            return true;
        }
        return super.applyPropertyCT(key, value);
    }

    @Override
    public AssemblerModulersBuilder copy() {
        return new AssemblerModulersBuilder(this);
    }

    public int getTire() {
        return (this.recipePropertyStorage == null) ? 0 :
                this.recipePropertyStorage.get(TierProperty.getInstance(), 0);
    }

    public int getCWUt() {
        return this.recipePropertyStorage == null ? 0 : this.recipePropertyStorage
                .get(ComputationProperty.getInstance(), 0);
    }

    public AssemblerModulersBuilder tier(int Tire) {
        if (Tire <= 0) {
            GTQTLog.logger.error("Tier cannot be less than or equal to 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(TierProperty.getInstance(), Tire);
        return this;
    }

    public AssemblerModulersBuilder CWUt(int cwut) {
        if (cwut < 0) {
            GTLog.logger.error("CWU/t cannot be less than 0", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(ComputationProperty.getInstance(), cwut);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(TierProperty.getInstance().getKey(), getTire())
                .append(ComputationProperty.getInstance().getKey(), getCWUt())
                .toString();
    }
}
