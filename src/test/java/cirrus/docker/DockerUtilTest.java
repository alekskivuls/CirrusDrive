package cirrus.docker;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

public class DockerUtilTest {
	@Test
	public void createTempSrcDir() {
		String folderPath = DockerUtil.createTmpSrcDir(Arrays.asList("body"), "java");
		assertTrue(new File(folderPath + "\0.java").exists());
	}
}
