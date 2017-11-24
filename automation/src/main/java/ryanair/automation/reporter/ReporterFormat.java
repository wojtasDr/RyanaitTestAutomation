package ryanair.automation.reporter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jbehave.core.failures.PendingStepStrategy;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;

public class ReporterFormat extends Format {

	private PendingStepStrategy pendingStepStrategy;

	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
		System.setProperty("current.date", dateFormat.format(new Date()));
	}

	public ReporterFormat() {
		super("APP");
	}

	@Override
	public StoryReporter createStoryReporter(FilePrintStreamFactory factory,
			StoryReporterBuilder storyReporterBuilder) {
		return new Reporter(pendingStepStrategy);
	}
}
