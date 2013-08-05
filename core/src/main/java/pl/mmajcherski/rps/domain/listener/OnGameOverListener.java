package pl.mmajcherski.rps.domain.listener;

import pl.mmajcherski.rps.domain.GameFinalScore;

public interface OnGameOverListener {

	void onGameOver(GameFinalScore gameScore);

}
