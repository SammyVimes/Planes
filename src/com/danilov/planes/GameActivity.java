package com.danilov.planes;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import com.danilov.planes.game.Game;
import com.danilov.planes.graphics.StaticTexture;
import com.danilov.planes.graphics.Textures;

public class GameActivity extends BaseGameActivity {
	
	private Game game;
	
	private Scene scene;
	private Camera camera;
	
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	@Override
	public EngineOptions onCreateEngineOptions() {
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		Textures textures = Textures.getTextures();
		textures.addTexture(StaticTexture.PLANE_NORMAL.getTexture());
		textures.load(getTextureManager(), this, 512, 512);
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		Textures textures = Textures.getTextures();
		textures.setVertexBufferObject(this.getVertexBufferObjectManager());
		scene = new Scene();

		scene.setBackground(new Background(Color.BLUE));
		
		game = new Game(this.mEngine, camera, scene);
		game.init(null);
		mEngine.setScene(scene);
		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

}
