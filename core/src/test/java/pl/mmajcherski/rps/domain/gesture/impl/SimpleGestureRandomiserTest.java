package pl.mmajcherski.rps.domain.gesture.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pl.mmajcherski.rps.domain.gesture.Gesture;

public class SimpleGestureRandomiserTest {
	
	private SimpleGestureRandomiser gestureRandomiser;
	
	@BeforeClass
	public void createSut() {
		gestureRandomiser = new SimpleGestureRandomiser(Rock.INSTANCE, Paper.INSTANCE, Scissors.INSTANCE);
	}
	
	@Test
	public void shouldNotGenerateNull() {
		// given
		int retryCount = 100;
		List<Gesture> generatedGestures = new ArrayList<>(retryCount);
		
		// when
		generatedGestures.add(gestureRandomiser.getRandomGesture());
		
		// then
		assertThat(generatedGestures).doesNotContainNull();
	}
	
	@Test
	public void shouldGenerateAtLEastOneGestureFromGivenType() {
		// given
		int retryCount = 100;
		List<Gesture> generatedGestures = new ArrayList<>(retryCount);
		
		// when
		for (int i=0; i<retryCount; i++) {
			generatedGestures.add(gestureRandomiser.getRandomGesture());
		}
		
		// then
		assertThat(generatedGestures).containsOnly(Rock.INSTANCE, Paper.INSTANCE, Scissors.INSTANCE);
	}
	
}
