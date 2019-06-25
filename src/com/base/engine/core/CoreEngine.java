package com.base.engine.core;

import com.base.engine.internalGame.Game;
import com.base.engine.rendering.Window;

public class CoreEngine {
//	public static final int WIDTH = 800;
//	public static final int HEIGHT = 600;
//	public static final String TITLE = "3D Engine";
//	public static final double FRAME_CAP = 5000.0;

	private int width, height;
	private double frameTime;
	private boolean isRunning;
	private String windowTitle;
	private Game game;
	private RenderingEngine renderEngine;

	public CoreEngine(double framerate, Game game) {
		this.isRunning = false;
		this.game = game;
		this.frameTime = 1.0 / framerate;

	}

//	private void initGL() {
//		System.out.println(RenderUtil.getOpenGLVersion());
//		RenderUtil.initGraphics();
//	}

	public void createWindow(int width, int height, String windowTitle) {
		this.width = width;
		this.height = height;
		this.windowTitle = windowTitle;

		Window.createWindow(width, height, windowTitle);
		this.renderEngine = new RenderingEngine();
		System.out.println(renderEngine.getOpenGLVersion());
		// initGL();

		// this.game.init();

	}

	public void init() {
		game.getRootObject().init();
	}

	public void start() {
		if (isRunning)
			return;

		run();
	}

	public void stop() {
		if (!isRunning)
			return;

		isRunning = false;
	}

	private void run() {
		game.init();
		isRunning = true;

		int frames = 0;
		long frameCounter = 0;

		final double frameTime = this.frameTime;

		long lastTime = Time.getTime();
		double unprocessedTime = 0;

		while (isRunning) {
			boolean render = false;

			long startTime = Time.getTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;

			unprocessedTime += passedTime / (double) Time.SECOND;
			frameCounter += passedTime;

			while (unprocessedTime > frameTime) {
				render = true;

				unprocessedTime -= frameTime;

				if (Window.isCloseRequested())
					stop();

				Time.setDelta(frameTime);

				game.input();
				Input.update();

				game.update();

				if (frameCounter >= Time.SECOND) {
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			if (render) {
				renderEngine.render(game.getRootObject());
				Window.render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		cleanUp();
	}

//	private void render() {
//		RenderUtil.clearScreen();
//		game.render();
//		Window.render();
//	}

	private void cleanUp() {
		Window.dispose();
	}

//	public static void main(String[] args)
//	{
//		Window.createWindow(WIDTH, HEIGHT, TITLE);
//		
//		MainComponent game = new MainComponent();
//		
//		game.start();
//	}
}
