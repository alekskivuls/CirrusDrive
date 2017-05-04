package cirrus.services;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import cirrus.docker.Docker;
import cirrus.docker.DockerUtil;
import cirrus.docker.Language;

@Service
public class DockerService {
	private Docker docker;
	private String currBuildDir;

	public DockerService() {
		docker = new Docker();
		currBuildDir = null;
	}

	public String buildProgram(String programSrc, Language lang) {
		currBuildDir = DockerUtil.createTmpSrcDir(Arrays.asList(programSrc), lang);
		String containerId = docker.createBuildContainer(lang, currBuildDir, "0." + lang.getExtension());
		docker.startContainer(containerId);
		docker.waitContainer(containerId);
		String logs = docker.readContainerLogs(containerId);
		docker.removeContainer(containerId);
		return logs;
	}

	public String runProgram(String programSrc, Language lang) {
		if (currBuildDir == null)
			return "";
		String containerId = docker.createRunContainer(lang, currBuildDir, "main");
		docker.startContainer(containerId);
		docker.waitContainer(containerId);
		String logs = docker.readContainerLogs(containerId);
		docker.removeContainer(containerId);
		return logs;
	}
}
