package com.danilov.planes.game.object.player;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import com.danilov.planes.game.GameWorld;
import com.danilov.planes.game.object.GameObject;
import com.danilov.planes.game.object.RotateState;
import com.danilov.planes.graphics.StaticTexture;
import com.danilov.planes.graphics.Textures;

public class Player extends GameObject {
	
	private PlaneState planeState;
	private RotateState rotateState = RotateState.RIGHT;
	private Entity plane;
	private Scene scene;
	private PhysicsHandler physicsHandler;
	private boolean isAttached = false;
	private float x;
	private float y;
	private int velocityY = 0;
	private int velocityX = 5;
	
	public Player(final GameWorld gameWorld, final float x, final float y) {
		super(gameWorld);
		scene = gameWorld.getScene(); 
		this.x = x;
		this.y = y;
		//TODO: remove after test
		velocityX = 25;
	}
	
	public void init() {
		physicsHandler = new PhysicsHandler(null); //do not forget to set entity or will cause NOP
		physicsHandler.setVelocity(velocityX, velocityY);
		changeState(PlaneState.NORMAL);
	}
	
	public void setRotateState(final RotateState rotateState) {
		this.rotateState = rotateState;
	}
	
	public void attachToScene() {
		plane.registerUpdateHandler(physicsHandler);
		scene.attachChild(plane);
		isAttached = true;
	}
	
	@Override
	public void onUpdate(float secondsElapsed) {
		//update coordinates TODO: decide if this is needed
		this.x = plane.getX();
		this.y = plane.getY();
	}
	
	public void changeState(final PlaneState state) {
		if (state == planeState) {
			return;
		}
		Textures textures = Textures.getTextures();
		this.planeState = state;
		if (isAttached) { //TODO: is logic clear? or maybe just check for null 
			plane.detachSelf();
		}
		isAttached = false;
		switch(planeState) {
		case DAMAGED:
			plane = null;
			break;
		case DESTROYED:
			plane = null;
			break;
		case NORMAL:
			plane = new Sprite(x, y, textures.getTextureRegion(StaticTexture.PLANE_NORMAL.getName()), textures.getVertexBufferObjectManager());
			break;
		}
		if (this.rotateState == RotateState.LEFT) {
			((Sprite) plane).setFlippedHorizontal(true);
		} else {
			((Sprite) plane).setFlippedHorizontal(false);
		}
		physicsHandler.setEntity(plane);
		attachToScene();
	}
	
}
