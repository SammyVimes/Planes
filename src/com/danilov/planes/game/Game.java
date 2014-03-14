package com.danilov.planes.game;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;

import com.danilov.planes.game.options.GameOptions;

public class Game implements IUpdateHandler {

	protected Camera camera;
	private Engine engine;
	private Scene scene;
	
	private GameWorld gameWorld;
	private AI ai;
	
	public Game(final Engine engine, final Camera camera, final Scene scene) {
		this.engine = engine;
		this.camera = camera;
		this.scene = scene;
		gameWorld = new GameWorld(this);
	}
	
	public void init(final GameOptions gameOptions) {
		gameWorld.init(gameOptions, scene);
		engine.registerUpdateHandler(this);
	}

	@Override
	public void onUpdate(final float secondsElapsed) {
		ai.onUpdate(secondsElapsed);
		gameWorld.onUpdate(secondsElapsed);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}
	
}
