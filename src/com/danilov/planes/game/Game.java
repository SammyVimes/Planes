package com.danilov.planes.game;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.opengl.GLES20;

import com.danilov.planes.game.controller.Controller;
import com.danilov.planes.game.controller.DevicePlayerController;
import com.danilov.planes.game.controller.command.PlayerControlsCommand;
import com.danilov.planes.game.object.Side;
import com.danilov.planes.game.object.player.Player;
import com.danilov.planes.game.options.GameOptions;
import com.danilov.planes.graphics.StaticTexture;
import com.danilov.planes.graphics.Textures;

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
	
	//TODO: stard doing in proper way
	Player p = null;
	
	public void init(final GameOptions gameOptions) {
		gameWorld.init(gameOptions, scene);
		p = new Player(gameWorld, 25, 25);
		DevicePlayerController controller = new DevicePlayerController(p);
		p.setController(controller);
		p.init();
		p.setLooksToThe(Side.LEFT);
		gameWorld.addObject(p);
		ai = new AI();
		engine.registerUpdateHandler(this);
		createHUD();
	}
	
	public void createHUD() {
		Textures textures = Textures.getTextures();
		ITextureRegion joystickBase = textures.getTextureRegion(StaticTexture.JOYSTICK_BASE.getName());
		ITextureRegion joystickKnob = textures.getTextureRegion(StaticTexture.JOYSTICK_KNOB.getName());
		AnalogOnScreenControl playerControl = new AnalogOnScreenControl(128, 250, camera, joystickBase, joystickKnob, 0.1f, textures.getVertexBufferObjectManager(), new IAnalogOnScreenControlListener() {
			
			private Side side = null;
			private Controller playerController = p.getController();
			
			@Override
			public void onControlChange(BaseOnScreenControl pBaseOnScreenControl,
					float pValueX, float pValueY) {
				Side tmpSide = null;
				PlayerControlsCommand command = null;
				if (pValueX < 0) {
					tmpSide = Side.LEFT;
				} else if (pValueX > 0) {
					tmpSide = Side.RIGHT;
				} else {
					side = null;	
					command = new PlayerControlsCommand(PlayerControlsCommand.Control.LEFT_RIGHT_UP);
				}
				if (tmpSide != side && tmpSide != null) {
					side = tmpSide;
					switch(side) {
					case LEFT:
						command = new PlayerControlsCommand(PlayerControlsCommand.Control.LEFT_PRESSED);
						break;
					case RIGHT:
						command = new PlayerControlsCommand(PlayerControlsCommand.Control.RIGHT_PRESSED);
						break;
					}
				}
				if (command != null) {
					playerController.sendCommand(command);
				}
			}
			
			@Override
			public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl) {
				// TODO Auto-generated method stub
				
			}
		});
		playerControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		playerControl.getControlBase().setAlpha(0.5f);
		scene.setChildScene(playerControl);
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
