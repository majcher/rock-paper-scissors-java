package pl.mmajcherski.rps;

import pl.mmajcherski.rps.impl.PlayerId;

public interface Game {

	void accept(Player player);

	Player getPlayerById(PlayerId playerName);

}
