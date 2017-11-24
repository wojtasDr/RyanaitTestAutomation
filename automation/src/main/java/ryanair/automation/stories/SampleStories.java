package ryanair.automation.stories;

import java.util.List;

import static java.util.Arrays.asList;

public class SampleStories extends AbstractStories {

	@Override
	protected List<String> storyPaths() {
		return asList("stories/FirstStory.story"
		);
	}
}
