package cirrus.docker;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

public class DockerTest {

	static Docker docker;

	@BeforeClass
	public static void setUpBeforeClass() {
		docker = new Docker();
	}

	@Test
	public void javaProgram() {
		String srcDir = DockerUtil.createTmpSrcDir(Arrays.asList(getJavaSrc()), Language.JAVA);
		String containerId = docker.createBuildContainer(Language.JAVA, srcDir, "0.java");
		docker.startContainer(containerId);
		docker.waitContainer(containerId);
		String logs = docker.readContainerLogs(containerId);
		docker.removeContainer(containerId);
		assertEquals("", logs);
		
		containerId = docker.createRunContainer(Language.JAVA, srcDir, "");
		docker.startContainer(containerId);
		docker.waitContainer(containerId);
		logs = docker.readContainerLogs(containerId);
		docker.removeContainer(containerId);
		assertEquals("Test\n", logs);
	}

	public static String getJavaSrc() {
		String programSrc = "class main {";
		programSrc += "public static void main(String[] args) {System.out.println(\"Test\");}}";
		return programSrc;
	}
}
