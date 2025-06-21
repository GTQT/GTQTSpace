package meowmel.gtqtspace.api.multiblock;

import net.minecraft.nbt.NBTTagCompound;

public final class Satellite {
	private int circuit;
	private final int solarTier;
	private final SatelliteSeniorUpdates seniorTier;
	private final SatelliteGenerators generatorTier;
	private int duration;
	private boolean using;

	public Satellite(int circuit, int solarTier, SatelliteSeniorUpdates seniorTier, SatelliteGenerators generatorTier, int duration, boolean using) {
        this.circuit = circuit;
		this.solarTier = solarTier;
		this.seniorTier = seniorTier;
		this.generatorTier = generatorTier;
		this.duration = duration;
		this.using = using;
	}

	public Satellite(int circuit, int solarTier, SatelliteSeniorUpdates seniorTier, SatelliteGenerators generatorTier) {
		this(circuit, solarTier, seniorTier, generatorTier, 114514, false);
	}

	public int getCircuit() {
		return circuit;
	}

	public int getSolarTier() {
		return solarTier;
	}

	public SatelliteSeniorUpdates getSeniorTier() {
		return seniorTier;
	}

	public SatelliteGenerators getGeneratorTier() {
		return generatorTier;
	}

	public int getDuration() {
		return duration;
	}

	public boolean isUsing() {
		return using;
	}

	public void setUsing(boolean using) {
		this.using = using;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setCircuit(int circuit) {
		this.circuit = circuit;
	}

	public static Satellite deserialize(NBTTagCompound nbt) {
		int circuit = nbt.getInteger("circuit");
		int solarTier = nbt.getInteger("solarTier");
		int seniorID = nbt.getInteger("seniorTier");
		int generatorID = nbt.getInteger("generatorTier");
		int duration = nbt.getInteger("duration");
		boolean using = nbt.getBoolean("using");
		return new Satellite(circuit, solarTier, SatelliteSeniorUpdates.getSeniorUpdateFromID(seniorID), SatelliteGenerators.getGeneratorFromID(generatorID), duration, using);
	}

	public NBTTagCompound serialize() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("circuit", this.circuit);
		nbt.setInteger("solarTier", this.solarTier);
		nbt.setInteger("generatorTier", this.generatorTier.getID());
		nbt.setInteger("seniorTier", this.seniorTier.getID());
		nbt.setInteger("duration", this.duration);
		nbt.setBoolean("using", this.using);
		return nbt;
	}
}
