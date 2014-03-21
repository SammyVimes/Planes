package com.danilov.planes.game.controller.command;

import java.util.List;

import com.danilov.planes.game.object.player.Player;

public class AICommand extends Command {

	private final float secondsElapsed;
	private final List<Player> players;
	
	public AICommand(final float secondsElapsed, final List<Player> players) {
		this.secondsElapsed = secondsElapsed;
		this.players = players;
	}
	
	public float getSecondsElapsed() {
		return secondsElapsed;
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	@Override
	public CommandType getCommandType() {
		return CommandType.AI_UPDATE;
	}
	
}
