package com.danilov.planes.game.object;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.util.color.Color;

import com.danilov.planes.game.GameWorld;
import com.danilov.planes.graphics.Textures;

public class Player extends GameObject {
	
	private Entity plane;
	private PhysicsHandler physicsHandler;
	private int x;
	private int y;
	private int velocityY = 0;
	private int velocityX = 5;
	
	public Player(final GameWorld gameWorld, final int x, final int y) {
		super(gameWorld);
		this.x = x;
		this.y = y;
		
		//TODO: remove after test
		velocityX = 5;
	}
	
	public void attachToScene(final Scene scene) {
		Textures textures = Textures.getTextures();
		plane = new Rectangle(x, y, 100, 50, textures.getVertexBufferObjectManager());
		plane.setColor(Color.RED);
		physicsHandler = new PhysicsHandler(plane);
		physicsHandler.setVelocity(velocityX, velocityY);
		plane.registerUpdateHandler(physicsHandler);
		scene.attachChild(plane);
	}
	
}
