package webcrawler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.scalable.webcrawler.Statistics;

public class StatisticsTest {

	private List<String> listOfLibraries;

	@Before
	public void init() {
		listOfLibraries = new ArrayList<>();
		listOfLibraries.add("Library1.js");
		listOfLibraries.add("Library1.js");
		listOfLibraries.add("Library1.js");

		listOfLibraries.add("Library2.js");
		listOfLibraries.add("Library2.js");
		listOfLibraries.add("Library2.js");
		listOfLibraries.add("Library2.js");
		listOfLibraries.add("Library2.js");

		listOfLibraries.add("Library3.js");
		listOfLibraries.add("Library3.js");
		listOfLibraries.add("Library3.js");
		listOfLibraries.add("Library3.js");
		listOfLibraries.add("Library3.js");
		listOfLibraries.add("Library3.js");
		listOfLibraries.add("Library3.js");

		listOfLibraries.add("Library4.js");
		listOfLibraries.add("Library4.js");

		listOfLibraries.add("Library5.js");
		listOfLibraries.add("Library5.js");
		listOfLibraries.add("Library5.js");
		listOfLibraries.add("Library5.js");

		listOfLibraries.add("Library6.js");

		listOfLibraries.add("Library7.js");
		listOfLibraries.add("Library7.js");
		listOfLibraries.add("Library7.js");
		listOfLibraries.add("Library7.js");
		listOfLibraries.add("Library7.js");
		listOfLibraries.add("Library7.js");
		listOfLibraries.add("Library7.js");
		listOfLibraries.add("Library7.js");
		listOfLibraries.add("Library7.js");
	}

	@Test
	public void testContainCorrectLibrariesSuccess() {
		Statistics statistics = new Statistics();

		Map<String, Long> countLibraries = statistics.countLibraries(listOfLibraries);

		assertTrue(countLibraries.containsKey("Library1.js"));
		assertTrue(countLibraries.containsKey("Library2.js"));
		assertTrue(countLibraries.containsKey("Library3.js"));
		assertTrue(countLibraries.containsKey("Library5.js"));
		assertTrue(countLibraries.containsKey("Library7.js"));
	}

	@Test
	public void testNotContainsLibraries() {
		Statistics statistics = new Statistics();

		Map<String, Long> countLibraries = statistics.countLibraries(listOfLibraries);

		assertFalse(countLibraries.containsKey("Library4.js"));
		assertFalse(countLibraries.containsKey("Library6.js"));
	}

	@Test
	public void testCountLibrariesSuccess() {
		Statistics statistics = new Statistics();

		Map<String, Long> countLibraries = statistics.countLibraries(listOfLibraries);

		assertTrue(countLibraries.get("Library1.js") == 3);
		assertTrue(countLibraries.get("Library2.js") == 5);
		assertTrue(countLibraries.get("Library3.js") == 7);
		assertTrue(countLibraries.get("Library5.js") == 4);
		assertTrue(countLibraries.get("Library7.js") == 9);
	}
}
