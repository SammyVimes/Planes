package com.danilov.planes.game.object;

import org.andengine.engine.handler.IUpdateHandler;

import com.danilov.planes.game.GameWorld;

public class GameObject implements IUpdateHandler {

	protected GameWorld gameWorld;
	
	public GameObject(final GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	protected GameWorld getGameWorld() {
		return gameWorld;
	}
	
	public void removeSelf() {
		this.gameWorld.removeObject(this);
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
