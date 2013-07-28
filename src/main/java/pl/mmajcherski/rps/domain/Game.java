package pl.mmajcherski.rps.domain;

import pl.mmajcherski.rps.domain.impl.PlayerId;

public interface Game {

	void accept(Player player);

	Player getPlayerById(PlayerId playerName);

	void onPlayerReadyToPlay(PlayerId playerId);

	GamePlayStatus getGamePlayStatusFor(PlayerId playerId);

}
