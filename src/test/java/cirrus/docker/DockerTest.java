package cirrus.docker;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

public class DockerTest {
	static String programSrc = "";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		programSrc = "class main {";
		programSrc += "public static void main(String[] args) {System.out.println(\"Test\");}}";
	}

	@Test
	public void fullTest() {
		Docker docker = new Docker();
		String srcDir = DockerUtil.createTmpSrcDir(Arrays.asList(programSrc), Language.JAVA);
		String containerId = docker.createContainer(Language.JAVA, srcDir, "0.java");
		docker.startContainer(containerId);
		docker.waitContainer(containerId);
		String logs = docker.readContainerLogs(containerId);
		docker.removeContainer(containerId);
		assertEquals("Test\n", logs);
	}
}
