package com.danilov.planes.game.controller.command;

public class PlayerControlsCommand extends Command {

	public enum Control {
		
		LEFT_PRESSED,
		RIGHT_PRESSED,
		LEFT_RIGHT_UP, //TODO: rename to control up
		FIRE_PRESSED,
		FIRE_UP
		
	}
	
	private Control control;
	
	public PlayerControlsCommand(final Control control) {
		this.control = control;
	}
	
	public Control getControl() {
		return control;
	}
	
	
	@Override
	public CommandType getCommandType() {
		return CommandType.PLAYER_CONTROLS;
	}

}
