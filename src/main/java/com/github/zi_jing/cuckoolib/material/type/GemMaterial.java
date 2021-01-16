package com.github.zi_jing.cuckoolib.material.type;

public class GemMaterial extends DustMaterial {
	public GemMaterial(String name, int color) {
		super(name, color);
	}

	static {
		registerDefaultFlags(GemMaterial.class, GENERATE_DUST, GENERATE_CRYSTAL, GENERATE_ROD, GENERATE_GEAR,
				GENERATE_SCREW, GENERATE_RING);
	}
}