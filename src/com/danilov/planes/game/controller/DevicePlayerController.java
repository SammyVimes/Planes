package com.danilov.planes.game.controller;

import com.danilov.planes.game.controller.command.Command;
import com.danilov.planes.game.controller.command.PlayerControlsCommand;
import com.danilov.planes.game.object.player.Player;

public class DevicePlayerController implements Controller {
	
	private Player player;
	
	public DevicePlayerController(final Player player) {
		this.player = player;
	}

	@Override
	public void sendCommand(final Command command) {
		switch(command.getCommandType()) {
		case PLAYER_CONTROLS:
			onPlayerControlsCommand((PlayerControlsCommand) command);
			break;
		default:
			break;
		}
	}
	
	private void onPlayerControlsCommand(final PlayerControlsCommand command) {
		switch (command.getControl()) {
		case FIRE_PRESSED:
			break;
		case FIRE_UP:
			break;
		case LEFT_PRESSED:
			player.startRotatingLeft();
			break;
		case RIGHT_PRESSED:
			player.startRotatingRight();
			break;
		case LEFT_RIGHT_UP:
			player.stopRotating();
			break;
		
		}
	}

}
