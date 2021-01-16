package com.github.zi_jing.cuckoolib.material.type;

public class DustMaterial extends Material {
	public DustMaterial(String name, int color) {
		super(name, color);
	}

	static {
		registerDefaultFlags(DustMaterial.class, GENERATE_DUST);
	}
}