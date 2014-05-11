package com.danilov.planes.game.object.weapon;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.Shape;

import com.danilov.planes.game.GameWorld;
import com.danilov.planes.game.object.GameObject;
import com.danilov.planes.game.object.player.Player;
import com.danilov.planes.graphics.Textures;
import com.danilov.planes.util.AngleUtils;

public class Bullet extends GameObject {
	
	private static float TIME_TO_LIVE = 1.5f; //1.5 sec
	private static long WIDTH_HEIGHT = 10;
	private static double DEFAULT_VELOCITY = 650;
	
	private Player shooter = null;
	
	private float lifeTime = 0f;

	private GameWorld gameWorld = null;
	
	private PhysicsHandler physicsHandler;
	
	private float x = 0;
	private float y = 0;
	
	private double velocity = 0;
	private double velocityX = 0;
	private double velocityY = 0;
	private double rotationAngle = 0;
	
	private Shape bulletShape = null;
	
	public Bullet(final Player shooter,
				final float x,
				final float y,
				final double angle,
				final GameWorld gameWorld) {
		super(gameWorld);
		this.x = x;
		this.y = y;
		this.shooter = shooter;
		this.gameWorld = gameWorld;
		this.rotationAngle = angle;
	}
	
	public void init() {
		Textures textures = Textures.getTextures();
		bulletShape = new Rectangle(x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, textures.getVertexBufferObjectManager());
		physicsHandler = new PhysicsHandler(bulletShape);
		bulletShape.registerUpdateHandler(physicsHandler);
		gameWorld.getScene().attachChild(bulletShape);
		this.velocity = DEFAULT_VELOCITY;
		bulletShape.setRotation(45.0f);
		setVelocity(velocity);
	}
	
	
	public void setVelocity(final double velocity) {
		this.velocity = velocity;
		double angleRad =  AngleUtils.toRad(rotationAngle);
		velocityX = Math.cos(angleRad) * velocity;
		velocityY = Math.sin(angleRad) * velocity;
		setPhysicsHandlerVelocity(velocityX, velocityY);
	}
	
	private void setPhysicsHandlerVelocity(final double xVel, final double yVel) {
		physicsHandler.setVelocity((float) velocityX, (float) velocityY);
	} 
	
	public void delete() {
		bulletShape.unregisterUpdateHandler(physicsHandler);
		bulletShape.detachSelf();
		this.removeSelf();
	}
	
	public Player getShooter() {
		return shooter;
	}

	@Override
	public void onUpdate(final float secondsElapsed) {
		lifeTime += secondsElapsed;
		if (lifeTime >= TIME_TO_LIVE) {
			delete();
		}
	}

}
