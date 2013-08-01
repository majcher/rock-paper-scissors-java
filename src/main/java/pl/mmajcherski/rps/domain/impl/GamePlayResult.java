package pl.mmajcherski.rps.domain.impl;

import java.util.HashMap;
import java.util.Map;

import pl.mmajcherski.rps.domain.GamePlayStatus;

public class GamePlayResult {

	private Map<PlayerId, GamePlayStatus> results = new HashMap<>();

	public void addPlayerGamePlayStatus(PlayerId playerId, GamePlayStatus gamePlayStatus) {
		results.put(playerId, gamePlayStatus);
	}

	public GamePlayStatus getGamePlayStatusOf(PlayerId playerId) {
		return results.get(playerId);
	}
	
}
