package pl.mmajcherski.rps.domain.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameScore {

	private Map<PlayerId, Integer> playersScore = new HashMap<>();
	
	public GameScore(Set<PlayerId> playerIds) {
		for (PlayerId playerId : playerIds) {
			playersScore.put(playerId, 0);
		}
	}

	public void increaseScoreForPlayer(PlayerId playerId) {
		requirePlayerWith(playerId);
		
		Integer score = playersScore.get(playerId);
		playersScore.put(playerId, score + 1);
	}

	public int of(PlayerId playerId) {
		requirePlayerWith(playerId);
		return playersScore.get(playerId);
	}
	
	private void requirePlayerWith(PlayerId playerId) {
		if (!playersScore.containsKey(playerId)) {
			throw new IllegalArgumentException("No such player in the game");
		}
	}

}
