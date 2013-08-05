package pl.mmajcherski.rps.domain.listener;

import pl.mmajcherski.rps.domain.GameFinalScore;
import pl.mmajcherski.rps.domain.GamePlayResult;

public interface OnGamePlayResultListener {

	void onGamePlayResult(GamePlayResult gamePlayResult, GameFinalScore gameScore);

}
