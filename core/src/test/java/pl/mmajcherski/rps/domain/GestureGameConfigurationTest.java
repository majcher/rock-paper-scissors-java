package pl.mmajcherski.rps.domain;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.GameConfiguration;

public class GestureGameConfigurationTest {
		
	@Test
	public void shouldProvideDefaultPlayCountIfNotSet() {
		// given
		GameConfiguration.Builder builder = new GameConfiguration.Builder();
		
		// when
		GameConfiguration configuration = builder.build();
		
		// then
		assertThat(configuration.getPlayCount()).isEqualTo(GameConfiguration.Builder.DEFAULT_PLAY_COUNT);
	}
	
	@Test
	public void shouldProvideGivenPlayCountIfAvailable() {
		// given
		GameConfiguration.Builder builder = new GameConfiguration.Builder();
		int playCount = 10;
		
		// when
		GameConfiguration configuration = builder.withGamePlayCount(playCount).build();
		
		// then
		assertThat(configuration.getPlayCount()).isEqualTo(playCount);
	}
	
	@Test
	public void shouldProvideDefaultPlayDurationIfNotSet() {
		// given
		GameConfiguration.Builder builder = new GameConfiguration.Builder();
		
		// when
		GameConfiguration configuration = builder.build();
		
		// then
		assertThat(configuration.getPlayDurationInMs()).isEqualTo(GameConfiguration.Builder.DEFAULT_PLAY_DURATION_MS);
	}
	
	@Test
	public void shouldProvideGivenPlayDurationAvailable() {
		// given
		GameConfiguration.Builder builder = new GameConfiguration.Builder();
		int gamePlayDurationInMs = 10000;
		
		// when
		GameConfiguration configuration = builder.withGameDurationInMs(gamePlayDurationInMs).build();
		
		// then
		assertThat(configuration.getPlayDurationInMs()).isEqualTo(gamePlayDurationInMs);
	}
	
	@Test
	public void shouldProvideDefaultPauseDurationIfNotSet() {
		// given
		GameConfiguration.Builder builder = new GameConfiguration.Builder();
		
		// when
		GameConfiguration configuration = builder.build();
		
		// then
		assertThat(configuration.getPauseDuration()).isEqualTo(GameConfiguration.Builder.DEFAULT_PAUSE_DURATION_MS);
	}
	
	@Test
	public void shouldProvideGivenPauseDurationAvailable() {
		// given
		GameConfiguration.Builder builder = new GameConfiguration.Builder();
		int gamePauseDurationInMs = 5000;
		
		// when
		GameConfiguration configuration = builder.withPauseDurationInMs(gamePauseDurationInMs).build();
		
		// then
		assertThat(configuration.getPauseDuration()).isEqualTo(gamePauseDurationInMs);
	}
	
}
