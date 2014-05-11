package com.danilov.planes.game.controller;

import java.util.List;
import java.util.Random;

import com.danilov.planes.game.controller.command.AICommand;
import com.danilov.planes.game.controller.command.Command;
import com.danilov.planes.game.object.player.Player;
import com.danilov.planes.util.AngleUtils;

public class AIController implements Controller {

	private Player aiPlayer;
	
	//TODO: use
	@SuppressWarnings("unused")
	private Player currentTarget;
	
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
	
	private static final float TIME_BETWEEN_ROTATIONS = 0.5f;
	private float timeFromLastRotation = 0;
	
	private void onUpdateAICommand(final AICommand aiCommand) {
		List<Player> players = aiCommand.getPlayers();
		timeFromLastRotation += aiCommand.getSecondsElapsed();
		if (timeFromLastRotation > TIME_BETWEEN_ROTATIONS) {
			for (Player player : players) {
				findEnemyAndRotate(player);
			}
			aiPlayer.fireCannon();
			timeFromLastRotation = 0;
		}
	}
	
	//TODO: find the best target and fight it
	private void findEnemyAndRotate(final Player player) {
		if (aiPlayer.isRotatingToSomeAngle()) {
			return;
		}
		if (player != aiPlayer) {
			if (!aiPlayer.isEnemyOf(player)) {
				return; //do nothing if teammate
			}
			float x1 = aiPlayer.getX(), y1 = aiPlayer.getY(),
				  x2 = player.getX(), y2 = player.getY();
			double angle = AngleUtils.calculateAngleBetweenPoints(x1, y1, x2, y2);
			aiPlayer.startRotatingToTheAngle(angle);
		}
	}
	
}
