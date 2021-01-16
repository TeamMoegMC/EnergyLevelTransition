package com.github.zi_jing.cuckoolib.material.type;

public class PlasticityMaterial extends DustMaterial {
	public PlasticityMaterial(String name, int color) {
		super(name, color);
	}

	static {
		registerDefaultFlags(PlasticityMaterial.class, GENERATE_DUST, GENERATE_PLATE, GENERATE_ROD, GENERATE_GEAR,
				GENERATE_INGOT, GENERATE_FOIL, GENERATE_SCREW, GENERATE_SPRING, GENERATE_RING, GENERATE_WIRE,
				GENERATE_ROTOR);
	}
}