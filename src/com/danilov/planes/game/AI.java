package com.danilov.planes.game;

import java.util.List;

import com.danilov.planes.game.controller.AIController;
import com.danilov.planes.game.controller.command.AICommand;
import com.danilov.planes.game.controller.command.Command;
import com.danilov.planes.game.object.player.Player;

public class AI {
	
	private float secondsElapsedSinceLastUpdate = 0;

	private List<AIController> aiControllers;
	
	private List<Player> players;
	
	public AI(final List<AIController> aiControllers, final List<Player> players) {
		this.aiControllers = aiControllers;
		this.players = players;
	}

	public void onUpdate(final float secondsElapsed) {
		if (aiControllers == null) {
			return;
		}
		secondsElapsedSinceLastUpdate += secondsElapsed;
		if (secondsElapsedSinceLastUpdate < 0.5f) {
			return;
		}
		Command command = new AICommand(secondsElapsedSinceLastUpdate, players);
		secondsElapsedSinceLastUpdate = 0;
		for (AIController controller : aiControllers) {
			controller.sendCommand(command);
		}
	}
	
}
