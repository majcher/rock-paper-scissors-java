package pl.mmajcherski.rps.domain;

import java.util.HashMap;
import java.util.Map;

import pl.mmajcherski.rps.domain.player.PlayerId;

public class GamePlayResult {

	private Map<PlayerId, GamePlayStatus> results = new HashMap<>();

	public void addPlayerGamePlayStatus(PlayerId playerId, GamePlayStatus gamePlayStatus) {
		results.put(playerId, gamePlayStatus);
	}

	public GamePlayStatus getGamePlayStatusOf(PlayerId playerId) {
		return results.get(playerId);
	}
	
}
