package com.xxzzsoftware.game;

import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Game implements ApplicationListener {
	
	private final static String TAG = "Game";
	private Texture robot;
	private Music background;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle rect;
	
	private int screenWidth = 0;
	private int screenHeight = 0;
	
	private int robotWidth = 0;;
	private int robotHeight = 0;

	@Override
	public void create() {
		// load the images for the droplet and the bucket, 48x48 pixels each
		robot = new Texture(Gdx.files.internal("ic_launcher.png"));
		robotWidth = robot.getWidth();
		robotHeight = robot.getHeight();
		
		// load the background "music"
		background = Gdx.audio.newMusic(Gdx.files.internal("background.mp3"));
		
		// start the playback of the background music immediately
		background.setLooping(true);
		//background.play();
	      
		
		screenWidth = Gdx.app.getGraphics().getWidth();
		screenHeight = Gdx.app.getGraphics().getHeight();
		
		Log.d(TAG, "With = "+screenWidth);
		Log.d(TAG, "Height"+screenHeight);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenHeight);
		batch = new SpriteBatch();

		// create a Rectangle to logically represent the robot
		rect = new Rectangle();
		// center the rectangle horizontally
		rect.x = screenWidth / 2 - robotWidth/2;
		// bottom left corner of the bucket is 20 pixels above the bottom screen edge
		rect.y = 20; 
		rect.width = robotWidth;
		rect.height = robotHeight;

	}


	@Override
	public void render() {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the rectangle
		batch.begin();
		batch.draw(robot, rect.x, rect.y);
		batch.end();
		
		// process user input
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			rect.x = touchPos.x - robotWidth / 2;
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)){
			rect.x -= 500 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)){ 
			rect.x += 500 * Gdx.graphics.getDeltaTime();
		}
		
		// make sure the bucket stays within the screen bounds
		if (rect.x < 0){
			rect.x = 0;
		}
		if (rect.x > screenWidth -robotWidth){
			rect.x = screenWidth - robotWidth;
		}
	    

	}
	

	@Override
	public void dispose() {
		// dispose of all the native resources
		robot.dispose();
		batch.dispose();
		background.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}