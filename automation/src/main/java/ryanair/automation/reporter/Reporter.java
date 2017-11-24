package ryanair.automation.reporter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.PendingStepFound;
import org.jbehave.core.failures.PendingStepStrategy;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.NullStoryReporter;

public class Reporter extends NullStoryReporter {

	private static final Logger APP = Logger.getLogger("APP");

	private Story story;
	private boolean failed;
	private boolean ignored;
	private String currentScenario;
	private static String currentStoryName;
	private boolean givenStory;
	private int subStories = 0;
	private PendingStepStrategy pendingStepStrategy;
	private Map<String, List<String>> storyFailedScenario;
	private Map<String, List<String>> storyFailedGivenStory;
	private boolean failedGivenStories;
	private static String timestamp = "";

	public Reporter(PendingStepStrategy pendingStepStrategy) {
		this.pendingStepStrategy = pendingStepStrategy;
	}

	static {
		timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
		setTimestamp(timestamp);
	}

	@Override
	public void beforeStory(Story story, boolean givenStory) {
		this.givenStory = givenStory;
		this.setStoryName(story.getName().toString());
		if (isNotGivenStory()) {
			this.story = story;
			storyFailedScenario = new HashMap<String, List<String>>();
			storyFailedGivenStory = new HashMap<String, List<String>>();
		} else {
			subStories++;
		}
	}

	@Override
	public void afterStory(boolean givenStory) {
		if (isNotGivenStory()) {
			if (isNamedStory() && !story.getMeta().getPropertyNames().contains("noreporter")) {
				if (storyFailedScenario.get(story.getName()) == null
						&& storyFailedGivenStory.get(story.getName()) == null) {
					APP.info(story.getDescription().asString() + ";PASSED;");
				} else {
					APP.info(story.getDescription().asString() + ";FAILED;");
				}
			}
		} else {
			subStories--;
		}

		if (subStories == 0) {
			this.givenStory = false;
		}
	}

	private boolean isNamedStory() {
		return !story.getDescription().asString().isEmpty();
	}

	private boolean isNotGivenStory() {
		return !givenStory;
	}

	public static String getTimestamp() {
		return timestamp;
	}

	public static void setTimestamp(String timestamp) {
		Reporter.timestamp = timestamp;
	}

	public void setStoryName(String currentStoryName) {
		Reporter.currentStoryName = currentStoryName;
	}

	public static String getStoryName() {
		return currentStoryName;
	}

	@Override
	public void beforeScenario(String title) {
		if (isNotGivenStory()) {
			title = title.replaceAll("\n", " ");
			currentScenario = title;
		}
	}

	@Override
	public void afterScenario() {
		if (isNotGivenStory()) {
			if (failed || failedGivenStories || ignored) {
				if (storyFailedScenario.get(story.getName()) == null) {
					storyFailedScenario.put(story.getName(), new ArrayList<String>());
				}
				if (failed || failedGivenStories) {
					storyFailedScenario.get(story.getName()).add(currentScenario);
					
					APP.info("STORY_NAME: " + story.getName() + "; DESCRIPTION: " + currentScenario
							+ "; RESULT: failed;");
				} else {

					APP.info("STORY_NAME: " + story.getName() + "; DESCRIPTION: " + currentScenario
							+ "; RESULT: ignored;");
				}
			} else {
				APP.info("STORY_NAME: " + story.getName() + "; DESCRIPTION: " + currentScenario
						+ "; RESULT: passed;");
			}
			currentScenario = null;
			failed = false;
			failedGivenStories = false;
			ignored = false;
		} else {
			if (failed || failedGivenStories || ignored) {
				if (storyFailedGivenStory.get(story.getName()) == null) {
					storyFailedGivenStory.put(story.getName(), new ArrayList<String>());
				}
				storyFailedGivenStory.get(story.getName()).add(currentScenario);
			}
			failedGivenStories = false;
		}
	}

	@Override
	public void pending(String step) {
		if (isNotGivenStory()) {
			if (pendingStepStrategy instanceof FailingUponPendingStep) {
				failed = true;
				new PendingStepFound(step);
			} else {
				ignored = true;
			}
		}
	}

	@Override
	public void failed(String step, Throwable cause) {
		if (isNotGivenStory()) {
			failed = true;
		} else {
			failedGivenStories = true;
		}
	}

	@Override
	public void notPerformed(String step) {
		if (isNotGivenStory()) {
			failed = true;
		} else {
			failedGivenStories = true;
		}
	}
}
