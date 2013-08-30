package pl.mmajcherski.rps.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import pl.mmajcherski.rps.domain.player.PlayerId;

public class GameFinalScore {
	
	private final Map<PlayerId, Integer> playersScore;
	
	private GameFinalScore(Map<PlayerId, Integer> newScore) {
		playersScore = Collections.unmodifiableMap(newScore);
	}
	
	public static GameFinalScore initialScore() {
		return new GameFinalScore(new HashMap<PlayerId, Integer>());
	}
	
	public GameFinalScore increaseScoreForPlayer(PlayerId playerId) {
		Map<PlayerId, Integer> newScore = new HashMap<>(playersScore);

		Integer playerScore = newScore.get(playerId);
		newScore.put(playerId, (playerScore == null) ? 1 : playerScore + 1);
		
		return new GameFinalScore(newScore);
	}

	public int of(PlayerId playerId) {
		Integer score = playersScore.get(playerId);
		return (score == null) ? 0 : score;
	}
	
}
