package com.danilov.planes.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.content.Context;

public class Textures {

	private BitmapTextureAtlas bitmapTextureAtlas;
	
	private Map<String, Texture> texturesMap;
	private List<Texture> textures;
	
	private int atlasHeight;
	private int atlasWidth;
	
	private boolean isLoaded = false;
	
	public Textures() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		textures = new ArrayList<Texture>();
	}
	
	public void addTexture(final Texture texture) {
		textures.add(texture);
	}
	
	public void load(final TextureManager textureManager, final Context context) {
		this.bitmapTextureAtlas = new BitmapTextureAtlas(textureManager, atlasWidth, atlasHeight, TextureOptions.BILINEAR);		
		Collections.sort(textures);
		texturesMap = new HashMap<String, Texture>();
		int curX = 0;
		int curY = 0;
		int maxHeight = 0;
		for (Texture texture : textures) {
			if (curX + texture.width > atlasWidth) {
				curX = 0;
				curY += maxHeight;
			}
			if (texture.height > maxHeight) {
				maxHeight = texture.height;
			}
			ITextureRegion region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.bitmapTextureAtlas, context, texture.path, curX, curY);
			curX += texture.width;
			texture.region = region;
			texture.isRegionLoaded = true;
			texturesMap.put(texture.name, texture);
		}
		isLoaded = true;
	}
	
	public ITextureRegion getTextureRegion(final String name) {
		if (!isLoaded) {
			return null;
		}
		return texturesMap.get(name).region;
	}
	
}
