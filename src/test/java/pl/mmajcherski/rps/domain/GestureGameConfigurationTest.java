package pl.mmajcherski.rps.domain;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.GestureGameConfiguration;

public class GestureGameConfigurationTest {
		
	@Test
	public void shouldProvideDefaultPlayCountIfNotSet() {
		// given
		GestureGameConfiguration.Builder builder = new GestureGameConfiguration.Builder();
		
		// when
		GestureGameConfiguration configuration = builder.build();
		
		// then
		assertThat(configuration.getPlayCount()).isEqualTo(GestureGameConfiguration.Builder.DEFAULT_PLAY_COUNT);
	}
	
	@Test
	public void shouldProvideGivenPlayCountIfAvailable() {
		// given
		GestureGameConfiguration.Builder builder = new GestureGameConfiguration.Builder();
		int playCount = 10;
		
		// when
		GestureGameConfiguration configuration = builder.withGamePlayCount(playCount).build();
		
		// then
		assertThat(configuration.getPlayCount()).isEqualTo(playCount);
	}
	
	@Test
	public void shouldProvideDefaultPlayDurationIfNotSet() {
		// given
		GestureGameConfiguration.Builder builder = new GestureGameConfiguration.Builder();
		
		// when
		GestureGameConfiguration configuration = builder.build();
		
		// then
		assertThat(configuration.getPlayDurationInMs()).isEqualTo(GestureGameConfiguration.Builder.DEFAULT_PLAY_DURATION_MS);
	}
	
	@Test
	public void shouldProvideGivenPlayDurationAvailable() {
		// given
		GestureGameConfiguration.Builder builder = new GestureGameConfiguration.Builder();
		int gamePlayDurationInMs = 10000;
		
		// when
		GestureGameConfiguration configuration = builder.withGameDurationInMs(gamePlayDurationInMs).build();
		
		// then
		assertThat(configuration.getPlayDurationInMs()).isEqualTo(gamePlayDurationInMs);
	}
	
}
