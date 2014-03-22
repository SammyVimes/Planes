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
import com.danilov.planes.util.AngleUtils;

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
	private double rotatingToTheDegree = 0; //degrees
	private double velocity = 95;
	private double velocityY = 0;
	private double velocityX = 0;
	
	private boolean isRotating = false;
	private boolean isRotatingToSomeAngle = false;
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
	
	private void setRotationAngle(final double angle) {
		rotationAngle = AngleUtils.normalizeAngle(angle);
		setVelocity(velocity);
		((Sprite) plane).setRotation((float) angle);
	}
	
	private void addRotationAngle(final double angle) {
		setRotationAngle(rotationAngle + angle); 
	}
	
	public void setVelocity(final double velocity) {
		this.velocity = velocity;
		double angleRad =  AngleUtils.toRad(rotationAngle);
		velocityX = Math.cos(angleRad) * velocity;
		velocityY = Math.sin(angleRad) * velocity;
		if (looksToThe == Side.LEFT) {
			velocityX = -velocityX;
			velocityY = -velocityY;
		}
		setPhysicsHandlerVelocity(velocityX, velocityY);
	}
	
	private void setPhysicsHandlerVelocity(final double xVel, final double yVel) {
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
			if (isRotatingToSomeAngle) {
				if ((int) rotatingToTheDegree == (int) rotationAngle) {
					stopRotatingToTheAngle();
				}
			}
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
	
	//according to the nose of aircraft (its not a simple rotation of sprite)
	public void startRotatingToTheAngle(final double angle) {
		this.rotatingToTheDegree = angle;
		if (looksToThe == Side.LEFT) {
			rotatingToTheDegree = rotatingToTheDegree + 180;
		}
		if (rotatingToTheDegree > rotationAngle + 180) {
			rotatingToThe = Side.LEFT;
		} else {
			rotatingToThe = Side.RIGHT;
		}
		isRotatingToSomeAngle = true;
		isRotating = true;
	}
	
	public void stopRotatingToTheAngle() {
		isRotating = false;
		isRotatingToSomeAngle = false;
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
