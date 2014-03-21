package com.danilov.planes.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.Context;

public class Textures {

	private BitmapTextureAtlas bitmapTextureAtlas;
	
	private VertexBufferObjectManager vertexBufferObjectManager;
	
	private Map<String, Texture> texturesMap;
	private List<Texture> textures;
	
	private int atlasHeight;
	private int atlasWidth;
	
	private boolean isLoaded = false;
	
	private static Textures instance;
	
	public static Textures getTextures() {
		if (instance == null) {
			instance = new Textures();
		}
		return instance;
	}
	
	private Textures() {
		textures = new ArrayList<Texture>();
		texturesMap = new HashMap<String, Texture>();
	}
	
	public void setVertexBufferObject(final VertexBufferObjectManager vertexBufferObjectManager) {
		this.vertexBufferObjectManager = vertexBufferObjectManager;
	}

	public VertexBufferObjectManager getVertexBufferObjectManager() {
		return vertexBufferObjectManager;
	}
	
	public void addTexture(final Texture texture) {
		textures.add(texture);
	}
	
	public void load(final TextureManager textureManager, final Context context, final int atlasWidth, final int atlasHeight) {
		this.atlasHeight = atlasHeight;
		this.atlasWidth = atlasWidth;
		this.bitmapTextureAtlas = new BitmapTextureAtlas(textureManager, atlasWidth, atlasHeight, TextureOptions.BILINEAR);		
		Collections.sort(textures);
		int curX = 0;
		int curY = 0;
		int maxHeight = 0;
		for (Texture texture : textures) {
			if (curX + texture.width > atlasWidth) {
				curX = 0;
				curY += maxHeight;
				maxHeight = 0;
			}
			if (curY + texture.height > atlasHeight) {
				//TODO: change to textures exception etc
				throw new RuntimeException("Atlas is not big enough"); 
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
		bitmapTextureAtlas.load();
		isLoaded = true;
	}
	
	public void unload() {
		texturesMap.clear();
		textures.clear();
		if (bitmapTextureAtlas != null) {
			bitmapTextureAtlas.unload();
		}
		bitmapTextureAtlas = null;
	}
	
	public ITextureRegion getTextureRegion(final String name) {
		if (!isLoaded) {
			return null;
		}
		return texturesMap.get(name).region;
	}
	
}
