package pl.mmajcherski.rps.domain.player;

import pl.mmajcherski.rps.domain.GestureGame;

public interface Player {

	PlayerId getId();
	
	void join(GestureGame game);

}
