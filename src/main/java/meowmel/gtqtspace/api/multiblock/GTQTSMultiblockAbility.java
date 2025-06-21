package meowmel.gtqtspace.api.multiblock;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import keqing.gtqtcore.api.capability.IBio;

public class GTQTSMultiblockAbility {
    public static final MultiblockAbility<ISpaceElevatorProvider> SpaceElevatorProvider_MULTIBLOCK_ABILITY = new MultiblockAbility<>("SpaceElevatorProvider",ISpaceElevatorProvider.class);
    public static final MultiblockAbility<ISpaceElevatorReceiver> SpaceElevatorReceiver_MULTIBLOCK_ABILITY = new MultiblockAbility<>("SpaceElevatorReceiver",ISpaceElevatorReceiver.class);
}
