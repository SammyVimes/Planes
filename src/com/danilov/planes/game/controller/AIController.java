package com.danilov.planes.game.controller;

import java.util.Random;

import com.danilov.planes.game.controller.command.AICommand;
import com.danilov.planes.game.controller.command.Command;
import com.danilov.planes.game.object.player.Player;

public class AIController implements Controller {

	private Player aiPlayer;
	
	public AIController(final Player player) {
		this.aiPlayer = player;
	}
	
	@Override
	public void sendCommand(final Command command) {
		switch (command.getCommandType()) {
		case AI_UPDATE:
			onUpdateAICommand((AICommand) command);
			break;
		case BUTTON_PRESSED:
		case PLAYER_CONTROLS:
		default:
			break;
		
		}
	}
	
	Random rand = new Random();
	
	private void onUpdateAICommand(final AICommand aiCommand) {
		if (rand.nextBoolean()) {
			aiPlayer.startRotatingRight();			
		} else {
			aiPlayer.stopRotating();
		}
	}
	
}
