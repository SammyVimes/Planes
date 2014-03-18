package com.danilov.planes.graphics;

public enum StaticTexture {

	PLANE_NORMAL(114, 76, "objects/plane_normal.png", "planeNormal"),
	PLANE_DAMAGED(114, 76, "", ""),
	PLANE_DESTROYED(114, 76, "", ""),
	JOYSTICK_BASE(128, 128, "objects/joystick_base.png", "joystickBase"),
	JOYSTICK_KNOB(64, 64, "objects/joystick_knob.png", "joystickKnob");
	
	int width;
	int height;
	String path;
	String name;
	
	private StaticTexture(final int width, final int height, final String path, final String name) {
		this.width = width;
		this.height = height;
		this.path = path;
		this.name = name;
	}
	
	public Texture getTexture() {
		return new Texture(name, path, width, height);
	}

	public String getName() {
		return name;
	}
	
}
