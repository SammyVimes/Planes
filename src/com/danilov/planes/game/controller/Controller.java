package com.danilov.planes.game.controller;

import com.danilov.planes.game.controller.command.Command;

public interface Controller {

	void sendCommand(final Command command);
	
}
