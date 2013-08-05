package pl.mmajcherski.rps.domain.listener;

import pl.mmajcherski.rps.domain.GameConfiguration;

public interface OnGamePlayStartedListener {

	void onGamePlayStarted(GameConfiguration configuration);

}
