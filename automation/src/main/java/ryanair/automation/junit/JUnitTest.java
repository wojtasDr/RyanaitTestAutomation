package ryanair.automation.junit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;

import ryanair.automation.stories.SampleStories;

public class JUnitTest {

	@Before
	public void setUp() {
	}

	@AfterClass
	public static void tearDown() {
	}

	@Test
	public void test() {
		SampleStories s = new SampleStories();
		try {
			s.run();
		} catch (ApplicationException e) {
			Assert.fail(e.getMessage());
		} catch (Throwable e) {
		}
	}

}
