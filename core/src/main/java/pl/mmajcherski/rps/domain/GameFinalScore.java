package pl.mmajcherski.rps.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pl.mmajcherski.rps.domain.player.PlayerId;

public class GameFinalScore {

	private final Map<PlayerId, Integer> playersScore = new ConcurrentHashMap<>();
	
	public void increaseScoreForPlayer(PlayerId playerId) {
		Integer score = playersScore.get(playerId);
		playersScore.put(playerId, (score == null) ? 1 : score + 1);
	}

	public int of(PlayerId playerId) {
		Integer score = playersScore.get(playerId);
		return (score == null) ? 0 : score;
	}
	
}
