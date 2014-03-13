package com.danilov.planes.game.object;

import org.andengine.engine.handler.IUpdateHandler;

import com.danilov.planes.game.GameWorld;

public class GameObject implements IUpdateHandler {

	private GameWorld gameWorld;
	
	public GameObject(final GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	protected GameWorld getGameWorld() {
		return gameWorld;
	}
	
	@Override
	public void onUpdate(final float secondsElapsed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
