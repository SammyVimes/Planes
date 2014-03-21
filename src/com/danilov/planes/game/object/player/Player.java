package com.danilov.planes.game.object.player;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.danilov.planes.game.GameWorld;
import com.danilov.planes.game.controller.Controller;
import com.danilov.planes.game.object.GameObject;
import com.danilov.planes.game.object.Side;
import com.danilov.planes.graphics.StaticTexture;
import com.danilov.planes.graphics.Textures;

public class Player extends GameObject {

	
	private static final double DEF_ROT_ANGLE = 120;
	
	private Entity plane;
	private Scene scene;
	private boolean isAttached = false;
	private PhysicsHandler physicsHandler;
	
	private PlaneState planeState;
	private Side looksToThe = Side.RIGHT;
	private float x;
	private float y;
	private double rotationAngle = 0; //degrees
	private double velocity = 95;
	private double velocityY = 0;
	private double velocityX = 0;
	
	private boolean isRotating = false;
	private Side rotatingToThe = null;
	
	
	private Controller controller;
	
	public Player(final GameWorld gameWorld, final float x, final float y) {
		super(gameWorld);
		scene = gameWorld.getScene(); 
		this.x = x;
		this.y = y;
	}
	
	public void setController(final Controller controller) {
		this.controller = controller;
	}
	
	public void init() {
		physicsHandler = new PhysicsHandler(null); //do not forget to set entity or will cause NOP
		changeState(PlaneState.NORMAL);
		setRotationAngle(0);
	}
	
	public void setLooksToThe(final Side looksToThe) {
		this.looksToThe = looksToThe;
		if (this.looksToThe == Side.LEFT) {
			setflippedHorizontal(true);
		} else {
			setflippedHorizontal(false);
		}
		setVelocity(velocity);
	}
	
	//TODO: move this method to SUPER
	public Entity getEntity() {
		return plane;
	}
	
	public void setRotationAngle(final double angle) {
		//making angle not bigger than 360 and not smaller than -360
		if (angle > 0) {
			rotationAngle = angle % 360;
		} else {
			rotationAngle = ((angle * (-1)) % 360) * (-1);
		}
		setVelocity(velocity);
		((Sprite) plane).setRotation((float) angle);
	}
	
	public void addRotationAngle(final double angle) {
		setRotationAngle(rotationAngle + angle); 
	}
	
	public void setVelocity(final double velocity) {
		this.velocity = velocity;
		double angleRad =  Math.PI  * rotationAngle / 180;
		velocityX = Math.cos(angleRad) * velocity;
		velocityY = Math.sin(angleRad) * velocity;
		if (looksToThe == Side.LEFT) {
			velocityX = -velocityX;
			velocityY = -velocityY;
		}
		setVelocity(velocityX, velocityY);
	}
	
	private void setVelocity(final double xVel, final double yVel) {
		physicsHandler.setVelocity((float) velocityX, (float) velocityY);
	}
	
	public void attachToScene() {
		plane.registerUpdateHandler(physicsHandler);
		scene.attachChild(plane);
		isAttached = true;
	}
	
	@Override
	public void onUpdate(final float secondsElapsed) {
		//update coordinates TODO: decide if this is needed
		this.x = plane.getX();
		this.y = plane.getY();
		performRotations(secondsElapsed);
	}
	
	@SuppressWarnings("incomplete-switch")
	private void performRotations(final float secondsElapsed) {
		if (isRotating) {
			switch (rotatingToThe) {
			case LEFT:
				addRotationAngle(-DEF_ROT_ANGLE * secondsElapsed);
				break;
			case RIGHT:
				addRotationAngle(DEF_ROT_ANGLE * secondsElapsed);
				break;
			}
		}
	}
	
	public void startRotatingLeft() {
		isRotating = true;
		rotatingToThe = Side.LEFT;
	}
	
	public void startRotatingRight() {
		isRotating = true;
		rotatingToThe = Side.RIGHT;
	}
	
	public void stopRotating() {
		isRotating = false;
		rotatingToThe = null;
	}
	
	public void changeState(final PlaneState state) {
		if (state == planeState) {
			return;
		}
		Textures textures = Textures.getTextures();
		this.planeState = state;
		if (isAttached && plane != null) { //TODO: is logic clear? or maybe just check for null 
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
		setLooksToThe(this.looksToThe);
		physicsHandler.setEntity(plane);
		attachToScene();
	}
	
	private void setflippedHorizontal(final boolean isFlipped) {
		if (plane == null) {
			return;
		}
		((Sprite) plane).setFlippedHorizontal(isFlipped);
	}

	public Controller getController() {
		return controller;
	}
	
}
