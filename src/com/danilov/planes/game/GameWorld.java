package com.danilov.planes.game;

import java.util.LinkedList;
import java.util.List;

import org.andengine.entity.scene.Scene;

import com.danilov.planes.game.object.GameObject;
import com.danilov.planes.game.object.player.Player;
import com.danilov.planes.game.options.GameOptions;

public class GameWorld {
	
	protected Game game;
	
	protected Scene scene;
	
	private List<GameObject> gameObjects;

	public GameWorld(final Game game) {
		this.game = game;
		this.gameObjects = new LinkedList<GameObject>();
	}
	
	public void init(final GameOptions gameOptions, final Scene scene) {
		this.scene = scene;
	}
	
	public void addObject(final GameObject gameObject) {
		this.gameObjects.add(gameObject);
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void onUpdate(final float secondsElapsed) {
		for (GameObject gameObject : gameObjects) {
			gameObject.onUpdate(secondsElapsed);
		}
	}
	
}
