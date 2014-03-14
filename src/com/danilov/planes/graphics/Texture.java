package com.danilov.planes.graphics;

import org.andengine.opengl.texture.region.ITextureRegion;

public class Texture implements Comparable<Texture> {
	
	protected int width;
	protected int height;
	protected String name;
	protected String path;
	protected ITextureRegion region;
	protected boolean isRegionLoaded = false;
	
	
	public Texture(final String name, final String path, final int width, final int height) {
		this.name = name;
		this.path = path;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public int compareTo(final Texture another) {
		int _width = another.width;
		int _height = another.height;
		int result = 0;
		if (width < _width) {
			if ((height / 2) < _height) {
				result = -1;
			} else {
				result = 1;
			}
		} else if (width > _width){
			if ((_height / 2) < height) {
				result = 1;
			} else {
				result = -1;
			}
		} else {
			if (height < _height) {
				result = -1;
			} else if (height > _height) {
				result = 1;
			} else {
				result = 0;
			}
		}
		return result;
	}
	
}
