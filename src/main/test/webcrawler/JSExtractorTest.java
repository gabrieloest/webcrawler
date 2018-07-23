package webcrawler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.scalable.webcrawler.JSExtractor;

public class JSExtractorTest {

	private static final String DOWNLOAD_FOLDER = "test";

	@Test
	public void testExtracteListContainsSuccess() {
		JSExtractor jsExtractor = new JSExtractor();

		List<String> listOfLibraries = jsExtractor.extractListOfLibraries(DOWNLOAD_FOLDER);

		assertTrue(listOfLibraries.contains("library1.js"));
		assertTrue(listOfLibraries.contains("library2.js"));
		assertTrue(listOfLibraries.contains("library3.js"));
		assertTrue(listOfLibraries.contains("library4.js"));
		assertTrue(listOfLibraries.contains("library5.js"));
		assertTrue(listOfLibraries.contains("library6.js"));
	}

	@Test
	public void testExtracteListNotContainsSuccess() {
		JSExtractor jsExtractor = new JSExtractor();

		List<String> listOfLibraries = jsExtractor.extractListOfLibraries(DOWNLOAD_FOLDER);

		assertFalse(listOfLibraries.contains("library7.js"));
	}
}
