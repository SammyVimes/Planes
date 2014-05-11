package com.danilov.planes.game;

import java.util.LinkedList;
import java.util.List;

import org.andengine.entity.scene.Scene;

import com.danilov.planes.game.object.GameObject;
import com.danilov.planes.game.options.GameOptions;

public class GameWorld {
	
	protected Game game;
	
	protected Scene scene;
	
	private List<GameObject> gameObjects;
	
	private List<GameObject> objectsToRemove;

	public GameWorld(final Game game) {
		this.game = game;
		this.gameObjects = new LinkedList<GameObject>();
		this.objectsToRemove = new LinkedList<GameObject>();
	}
	
	public void init(final GameOptions gameOptions, final Scene scene) {
		this.scene = scene;
	}
	
	public void addObject(final GameObject gameObject) {
		this.gameObjects.add(gameObject);
	}
	
	public void removeObject(final GameObject gameObject) {
		scheduleDeletion(gameObject);
	}
	
	private void scheduleDeletion(final GameObject gameObject) {
		this.objectsToRemove.add(gameObject);
	}
	
	public void scheduleDeletion(final GameObject gameObject, final float ttl) {
		//todo:add TTL and lifeTime fields to GameObject and add another method that would handle deletions itself
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void onUpdate(final float secondsElapsed) {
		for (GameObject gameObject : objectsToRemove) {
			gameObjects.remove(gameObject);
		}
		objectsToRemove.clear();
		for (GameObject gameObject : gameObjects) {
			gameObject.onUpdate(secondsElapsed);
		}
	}
	
}
