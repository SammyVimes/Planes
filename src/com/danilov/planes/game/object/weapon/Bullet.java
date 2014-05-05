package com.danilov.planes.game.object.weapon;

import org.andengine.engine.handler.physics.PhysicsHandler;

import com.danilov.planes.game.GameWorld;
import com.danilov.planes.game.object.GameObject;
import com.danilov.planes.game.object.player.Player;

public class Bullet extends GameObject {
	
	private static long TIME_TO_LIVE = 1500; //1.5 sec
	
	private Player shooter = null;
	
	private long timeLeftToLive = 0;

	private PhysicsHandler physicsHandler;
	
	private float x = 0;
	private float y = 0;
	
	private int xVelocity = 0;
	private int yVelocity = 0;
	private double rotationAngle = 0;
	
	public Bullet(final Player shooter,
				final float x,
				final float y,
				final GameWorld gameWorld) {
		super(gameWorld);
		this.x = x;
		this.y = y;
		this.shooter = shooter;
	}

	@Override
	public void onUpdate(final float secondsElapsed) {
	}

}
