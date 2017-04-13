package cirrus.services;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import cirrus.docker.Docker;
import cirrus.docker.DockerUtil;
import cirrus.docker.Language;

@Service
public class DockerService {
	private Docker docker;

	public DockerService() {
		docker = new Docker();
	}

	public String runProgram(String programSrc) {
		String srcDir = DockerUtil.createTmpSrcDir(Arrays.asList(programSrc), "java");
		String containerId = docker.createContainer(Language.JAVA, srcDir, "0.java");
		docker.startContainer(containerId);
		docker.waitContainer(containerId);
		String logs = docker.readContainerLogs(containerId);
		docker.removeContainer(containerId);
		return logs;
	}
}
