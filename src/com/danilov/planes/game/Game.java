package com.danilov.planes.game;

import java.util.LinkedList;
import java.util.List;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.util.Log;
import android.view.MotionEvent;

import com.danilov.planes.game.controller.AIController;
import com.danilov.planes.game.controller.Controller;
import com.danilov.planes.game.controller.DevicePlayerController;
import com.danilov.planes.game.controller.command.PlayerControlsCommand;
import com.danilov.planes.game.controller.command.PlayerControlsCommand.Control;
import com.danilov.planes.game.object.player.Player;
import com.danilov.planes.game.options.GameOptions;
import com.danilov.planes.graphics.StaticTexture;
import com.danilov.planes.graphics.Textures;
import com.danilov.planes.util.Side;

public class Game implements IUpdateHandler {

	private Context context = null;
	
	protected Camera camera;
	private Engine engine;
	private Scene scene;
	
	private TMXTiledMap tmxMap =  null;
	
	private GameWorld gameWorld;
	private AI ai;
	
	private Player mainPlayer = null;
	
	public Game(final Engine engine, final Camera camera, final Scene scene, final Context context) {
		this.engine = engine;
		this.camera = camera;
		this.scene = scene;
		this.context = context;
		gameWorld = new GameWorld(this);
	}
	
	//TODO: start doing in proper way
	private List<Player> players = new LinkedList<Player>();
	
	public void init(final GameOptions gameOptions) {
		
		//TODO: rewrite
		loadMap();
		final TMXLayer mTMXLayer = tmxMap.getTMXLayers().get(0);
		scene.attachChild(mTMXLayer);
		
		TMXObject obj = tmxMap.getTMXObjectGroups().get(0).getTMXObjects().get(0);
		int x = obj.getX();
		int y = obj.getY();
		
		gameWorld.init(gameOptions, scene);
		
		mainPlayer = new Player(gameWorld, x, y);
		DevicePlayerController controller = new DevicePlayerController(mainPlayer);
		mainPlayer.setController(controller);
		mainPlayer.init();
		gameWorld.addObject(mainPlayer);
		mainPlayer.setTeam(0);
		
		Player aiPlayer1 = new Player(gameWorld, x + 70, y - 120);
		AIController aiController = new AIController(aiPlayer1);
		aiPlayer1.setController(aiController);
		aiPlayer1.init();
		aiPlayer1.setLooksToThe(Side.LEFT);
		

		
		Player aiPlayer2 = new Player(gameWorld, x + 270, y);
		AIController aiController2 = new AIController(aiPlayer2);
		aiPlayer2.setController(aiController2);
		aiPlayer2.init();
		aiPlayer2.setLooksToThe(Side.RIGHT);
		
		aiPlayer1.setTeam(1);
		aiPlayer2.setTeam(1);
		
		gameWorld.addObject(aiPlayer1);
		gameWorld.addObject(aiPlayer2);
		List<AIController> controllersOfAi = new LinkedList<AIController>();
		controllersOfAi.add(aiController);
		controllersOfAi.add(aiController2);
		players.add(aiPlayer1);
		players.add(aiPlayer2);
		players.add(mainPlayer);
		
		camera.setChaseEntity(mainPlayer.getEntity());
		
		ai = new AI(controllersOfAi, players);
		engine.registerUpdateHandler(this);
		createHUD();
	}
	
	public void createHUD() {
		Textures textures = Textures.getTextures();
		ITextureRegion joystickBase = textures.getTextureRegion(StaticTexture.JOYSTICK_BASE.getName());
		ITextureRegion joystickKnob = textures.getTextureRegion(StaticTexture.JOYSTICK_KNOB.getName());
		AnalogOnScreenControl playerControl = new AnalogOnScreenControl(80, 350, camera, joystickBase, joystickKnob, 0.1f, textures.getVertexBufferObjectManager(), new IAnalogOnScreenControlListener() {
			
			private Side side = null;
			private Controller playerController = mainPlayer.getController();
			
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
					command = new PlayerControlsCommand(PlayerControlsCommand.Control.LR_CONTROL_UP);
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
					default:
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
		
		Sprite fireBtn = new Sprite(750, 350, textures.getTextureRegion(StaticTexture.JOYSTICK_KNOB.getName()), textures.getVertexBufferObjectManager()) {
			
			private Controller playerController = mainPlayer.getController();
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				PlayerControlsCommand command = null;
				if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_UP) {
					command = new PlayerControlsCommand(Control.FIRE_UP);
					Log.d("Planes", "FIRE_UP");
				} else if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
					command = new PlayerControlsCommand(Control.FIRE_PRESSED);
					Log.d("Planes", "FIRE_PRESSED");
				}
				if (command != null) {
					playerController.sendCommand(command);
				}
				return true;
			}
		};
		fireBtn.setScale(2);
		playerControl.attachChild(fireBtn);
		playerControl.registerTouchArea(fireBtn);
	}
	
	private void loadMap() {
		AssetManager assetManager = context.getAssets();
		TMXLoader loader = new TMXLoader(assetManager, engine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, null);
		try {
			tmxMap = loader.loadFromAsset("tmx/map.tmx");
		} catch (TMXLoadException e) {
			Log.e("Planes.MapLoader", e.getMessage());
		}
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
