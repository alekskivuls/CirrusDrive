package cirrus.docker;

import java.util.ArrayList;
import java.util.List;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerClient.LogsParam;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.HostConfig.Bind;

public class Docker {
	DockerClient docker;

	public Docker() {
		// Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
		try {
			docker = DefaultDockerClient.fromEnv().build();
		} catch (DockerCertificateException e) {
			e.printStackTrace();
		}
	}

	public String createContainer(Language lang, String srcDir, String mainFile, String... srcFiles)
			throws DockerCertificateException, DockerException, InterruptedException, Exception {
		String srcs = "src/" + mainFile;
		for (String src : srcFiles) {
			srcs += " src/" + src;
		}
		switch (lang) {
		case JAVA:
			return createContainer(srcDir, "javac -encoding UTF-8 -sourcepath . -d . " + srcs,
					"java " + mainFile.substring(0, mainFile.lastIndexOf('.')));
		case PYTHON:
			return createContainer(srcDir, "python3 src/" + mainFile);
		case CPP:
			return createContainer(srcDir, "g++ " + srcs, "./a.out");
		default:
			throw new Exception("Language not supported");
		}
	}

	public String createContainer(String mountDir, String... args)
			throws DockerCertificateException, DockerException, InterruptedException {
		List<String> list = new ArrayList<>();
		for (String arg : args) {
			list.add(arg);
		}
		return createContainer(mountDir, list);
	}

	public String createContainer(String mountDir, List<String> commands)
			throws DockerCertificateException, DockerException, InterruptedException {

		String srcPath = Docker.class.getResource(mountDir).getPath();
		final HostConfig hostConfig = HostConfig.builder()
				.appendBinds(Bind.from(srcPath).to("/src").readOnly(true).build()).build();
		String shellScript = "";
		for (String cmd : commands) {
			shellScript += cmd + ';';
		}
		final ContainerConfig containerConfig = ContainerConfig.builder().hostConfig(hostConfig).image("cirrusbox")
				.cmd("sh", "-c", shellScript).build();

		final ContainerCreation creation = docker.createContainer(containerConfig);
		final String containerId = creation.id();
		return containerId;
	}

	public String getContainerInfo(String containerId) {
		ContainerInfo info = null;
		try {
			info = docker.inspectContainer(containerId);
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
		if (info != null)
			return info.toString();
		else
			return null;

	}

	public void startContainer(String containerId) {
		try {
			docker.startContainer(containerId);
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void waitContainer(String containerId) {
		try {
			docker.waitContainer(containerId);
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String readContainerLogs(String containerId) {
		String logs = null;
		try (LogStream stream = docker.logs(containerId, LogsParam.stdout(), LogsParam.stderr())) {
			logs = stream.readFully();
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
		return logs;
	}

	public void killContainer(String containerId) {
		try {
			docker.killContainer(containerId);
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void removeContainer(String containerId) {
		try {
			docker.removeContainer(containerId);
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
