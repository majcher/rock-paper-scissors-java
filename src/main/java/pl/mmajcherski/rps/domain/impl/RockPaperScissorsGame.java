package pl.mmajcherski.rps.domain.impl;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.mmajcherski.rps.domain.Game;
import pl.mmajcherski.rps.domain.GamePlayStatus;
import pl.mmajcherski.rps.domain.HandGesture;
import pl.mmajcherski.rps.domain.Player;

public class RockPaperScissorsGame implements Game {

private static final int PLAYERS_LIMIT = 2;
	
	private Map<PlayerId, Player> players = new HashMap<>();

	@Override
	public void accept(Player player) {
		requireNonNull(player, "Player cannot be null");
		
		checkGameCanAcceptPlayer(player);
		
		players.put(player.getId(), player);
	}

	@Override
	public Player getPlayerById(PlayerId playerId) {
		requireNonNull(playerId);
		
		checkThatGameHasPlayer(playerId);
		
		return players.get(playerId);
	}
	
	@Override
	public void onPlayerReadyToPlay(PlayerId playerId) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public GamePlayStatus getGamePlayStatusFor(PlayerId playerId) {
		Player player = players.get(playerId);
		HandGesture playerGesture = player.getGestureShown();
		
		Player opponent = getOpponent(playerId);
		HandGesture opponentGesture = opponent.getGestureShown();
		
		return playerGesture.compareToGesture(opponentGesture);
	}
	
	private Player getOpponent(PlayerId playerId) {
		List<PlayerId> allPlayersIds = new ArrayList<>(players.keySet());
		allPlayersIds.remove(playerId);
		return players.get(allPlayersIds.get(0));
	}

	private void checkGameCanAcceptPlayer(Player player) {
		checkPlayersSizeLimitReached();
		checkThatGameDoesNotHavePlayer(player.getId());
	}

	private void checkPlayersSizeLimitReached() {
		if (players.size() >= PLAYERS_LIMIT) {
			throw new IllegalStateException(String.format("Game has a limit of %d players", PLAYERS_LIMIT));
		}
	}
	
	private void checkThatGameHasPlayer(PlayerId playerId) {
		if (!players.containsKey(playerId)) {
			throw new IllegalArgumentException(String.format("No player with ID: %s in this game", playerId));
		}
	}
	
	private void checkThatGameDoesNotHavePlayer(PlayerId playerId) {
		if (players.containsKey(playerId)) {
			throw new IllegalArgumentException(String.format("Player with ID: %s has already joined the game", playerId));
		}
	}

}
