package pl.mmajcherski.rps.domain;

import java.util.HashMap;
import java.util.Map;

import pl.mmajcherski.rps.domain.player.PlayerId;
import pl.mmajcherski.rps.domain.player.Players;

public class GameScore {

	private final Players players;
	private final Map<PlayerId, Integer> playersScore = new HashMap<>();
	
	public GameScore(Players players) {
		this.players = players;
	}

	public void increaseScoreForPlayer(PlayerId playerId) {
		requirePlayerWith(playerId);
		
		Integer score = playersScore.get(playerId);
		playersScore.put(playerId, (score == null) ? 1 : score + 1);
	}

	public int of(PlayerId playerId) {
		requirePlayerWith(playerId);
		
		Integer score = playersScore.get(playerId);
		return (score == null) ? 0 : score;
	}
	
	private void requirePlayerWith(PlayerId playerId) {
		if (!players.contains(playerId)) {
			throw new IllegalArgumentException("No such player in the game");
		}
	}

}
