package com.project.util;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DirectoryStructureBuilderTest {

	@Before
	public void setUp() {
	}

	@Test
	public void testBuildDirectoryStructure_HappyPath() {
		Map<String, List<String>> directoryStructure = DirectoryStructureBuilder
				.buildDirectoryStructure("./src/test/resources/testInput/");
		Assert.assertEquals(1, directoryStructure.get("/").size());
		Assert.assertEquals(0, directoryStructure.get("/testDirectory4_emptyDirectory/").size());
		Assert.assertEquals(3, directoryStructure.get("/testDirectory3/").size());
		Assert.assertEquals(3, directoryStructure.get("/testDirectory2/").size());
		Assert.assertEquals(2, directoryStructure.get("/testDirectory1/").size());
		Assert.assertEquals(2, directoryStructure.get("/testDirectory1/inside1_testDirectory1/").size());
		Assert.assertEquals(2, directoryStructure.get("/testDirectory1/inside2_testDirectory1/").size());
		Assert.assertEquals(3, directoryStructure.get("/testDirectory2/inside1_testDirectory2/").size());
		Assert.assertEquals(0, directoryStructure.get("/testDirectory2/inside2_testDirectory2_emptyDirectory/").size());
		Assert.assertTrue(directoryStructure.get("/").contains("app.properties"));
		Assert.assertTrue(directoryStructure.get("/testDirectory1/").contains("file1_testDirectory1"));
		Assert.assertTrue(directoryStructure.get("/testDirectory2/").contains("file1_testDirectory2"));
		Assert.assertTrue(directoryStructure.get("/testDirectory3/").contains("file1_testDirectory3"));
		Assert.assertTrue(directoryStructure.get("/testDirectory1/inside1_testDirectory1/")
				.contains("file1_inside1_testDirectory1"));
		Assert.assertTrue(directoryStructure.get("/testDirectory1/inside2_testDirectory1/")
				.contains("file1_inside2_testDirectory1"));
		Assert.assertTrue(directoryStructure.get("/testDirectory2/inside1_testDirectory2/")
				.contains("file1_inside1_testDirectory2"));
	}

	@Test
	public void testBuildDirectoryStructure_ForEmptyDirectory() {
		Map<String, List<String>> directoryStructure = DirectoryStructureBuilder
				.buildDirectoryStructure("./src/test/resources/testDirectory4_emptyDirectory/");
		Assert.assertTrue(directoryStructure.get("/").isEmpty());
	}

}
