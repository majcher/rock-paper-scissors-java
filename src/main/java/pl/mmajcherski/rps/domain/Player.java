package pl.mmajcherski.rps.domain;

import pl.mmajcherski.rps.domain.impl.GestureGame;
import pl.mmajcherski.rps.domain.impl.PlayerId;

public interface Player {

	PlayerId getId();
	
	void join(GestureGame game);

}
