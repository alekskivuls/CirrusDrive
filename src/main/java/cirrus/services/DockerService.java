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

	public String buildProgram(String programSrc, Language lang) {
		String srcDir = DockerUtil.createTmpSrcDir(Arrays.asList(programSrc), lang);
		String containerId = docker.createContainer(lang, srcDir, "0." + lang.getExtension());
		String logs = docker.readContainerLogs(containerId);
		docker.removeContainer(containerId);
		return logs;
	}

	public String runProgram(String programSrc, Language lang) {
		String srcDir = DockerUtil.createTmpSrcDir(Arrays.asList(programSrc), lang);
		String containerId = docker.createContainer(lang, srcDir, "0." + lang.getExtension());
		docker.startContainer(containerId);
		docker.waitContainer(containerId);
		String logs = docker.readContainerLogs(containerId);
		docker.removeContainer(containerId);
		return logs;
	}
}
