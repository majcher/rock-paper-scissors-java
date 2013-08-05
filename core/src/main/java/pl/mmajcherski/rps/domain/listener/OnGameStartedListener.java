package pl.mmajcherski.rps.domain.listener;

import pl.mmajcherski.rps.domain.GameConfiguration;

public interface OnGameStartedListener {

	void onGameStarted(GameConfiguration configuration);

}
