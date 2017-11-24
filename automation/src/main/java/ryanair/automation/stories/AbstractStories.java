package ryanair.automation.stories;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.XML;


import java.awt.Dimension;
import java.awt.Toolkit;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.PendingStepStrategy;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.ParameterConverters.BooleanConverter;
import org.jbehave.core.steps.spring.SpringApplicationContextFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.jbehave.web.selenium.ContextView;
import org.jbehave.web.selenium.LocalFrameContextView;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumContextOutput;
import org.jbehave.web.selenium.SeleniumStepMonitor;
import org.springframework.context.ApplicationContext;

import ryanair.automation.reporter.ReporterFormat;


public abstract class AbstractStories extends JUnitStories {
	
    public AbstractStories() {
        configuredEmbedder().embedderControls().useStoryTimeoutInSecs(1000);
    }
	
	private PendingStepStrategy pendingStepStrategy = new FailingUponPendingStep();
    private CrossReference crossReference = new CrossReference()
    		.withJsonOnly()
    		.withPendingStepStrategy(pendingStepStrategy)
            .withOutputAfterEachStory(true)
            .excludingStoriesWithNoExecutedScenarios(true);
    		
    private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private int cvWidth = 600;
    private int cvHeight = 40;
    private ContextView contextView = new LocalFrameContextView()
    	.sized(cvWidth, cvHeight).located(d.width - (cvWidth + 150), d.height - cvHeight);
    private SeleniumContext seleniumContext = new SeleniumContext();
    private SeleniumStepMonitor stepMonitor = new SeleniumStepMonitor(
    		contextView, 
    		seleniumContext,
            crossReference.getStepMonitor()
    	);
    

    private Format[] formats = new Format[] { 
    		new SeleniumContextOutput(seleniumContext), 
    		CONSOLE, 
    		XML, 
    		new ReporterFormat() 
    	};
	
    private StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
            .withCodeLocation(codeLocationFromClass(this.getClass()))
            .withFailureTrace(true)
            .withFailureTraceCompression(true)
            .withDefaultFormats()
            .withFormats(formats)
            .withCrossReference(crossReference)
            .withFailureTrace(true);
    
    
    @Override
    public Configuration configuration() {
    	StoryControls storyControls = new StoryControls();
    	storyControls = storyControls.doResetStateBeforeScenario(false);
    	storyControls = storyControls.doResetStateBeforeStory(false);
    	StoryLoader storyLoader = new LoadFromClasspath(this.getClass());
    	ParameterControls parameterControls = new ParameterControls().useDelimiterNamedParameters(true);
        return new SeleniumConfiguration().useSeleniumContext(seleniumContext)
                .usePendingStepStrategy(pendingStepStrategy)
                .useStoryControls(storyControls)
                .useStepMonitor(stepMonitor)
                .useStoryLoader(storyLoader)
                .useStoryReporterBuilder(reporterBuilder)
                .useParameterControls(parameterControls)
                .useParameterConverters(new ParameterConverters()
				.addConverters(new BooleanConverter()));
    }
    
    @Override
    public InjectableStepsFactory stepsFactory() {
        ApplicationContext context = new SpringApplicationContextFactory("Configuration-beans.xml").createApplicationContext();
        return new SpringStepsFactory(configuration(), context);
    }
}

