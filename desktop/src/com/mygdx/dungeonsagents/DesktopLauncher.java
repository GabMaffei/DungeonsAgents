package com.mygdx.dungeonsagents;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.dungeonsagents.DungeonsAgents;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1280, 720);
		config.useVsync(true);
		config.setTitle("Dungeons & Agents");
		config.setForegroundFPS(60);
		int samples = 2; // you can also play around with higher values like 4
		config.setBackBufferConfig(8, 8, 8, 8, 16, 0, samples); // 8, 8, 8, 8, 16, 0 are default values
		new Lwjgl3Application(new DungeonsAgents(), config);
	}
}
