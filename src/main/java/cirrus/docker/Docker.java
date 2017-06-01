package cirrus.docker;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerClient.LogsParam;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.ProgressHandler;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.exceptions.ImageNotFoundException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.HostConfig.Bind;
import com.spotify.docker.client.messages.HostConfig.Builder;
import com.spotify.docker.client.messages.ProgressMessage;

public class Docker {
	private DockerClient docker;
	/** Destination to mount file inside container */
	private final String MOUNT_DEST = "/mnt";
	private final String IMAGE_NAME = "cirrusbox";

	public Docker() {
		// Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
		try {
			docker = DefaultDockerClient.fromEnv().build();
		} catch (DockerCertificateException e) {
			e.printStackTrace();
		}
		// Test if the image is built yet, build it if it's not
		try {
			docker.inspectImage(IMAGE_NAME);
		} catch (ImageNotFoundException e) {
			buildImage();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String createBuildContainer(Language lang, String srcDir, String mainFile, String... srcFiles) {
		String srcs = mainFile;
		for (String src : srcFiles) {
			srcs += " " + src;
		}
		String containerName = null;
		try {
			containerName = createContainer(srcDir, lang.getBuildCmd() + srcs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return containerName;
	}

	public String createRunContainer(Language lang, String binDir, String mainFile) {
		String containerName = null;
		try {
			containerName = createContainer(binDir, lang.getRunCmd() + mainFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return containerName;
	}

	private String createContainer(String mountDir, String... args)
			throws DockerCertificateException, DockerException, InterruptedException {
		List<String> list = new ArrayList<>();
		for (String arg : args) {
			list.add(arg);
		}
		return createContainer(mountDir, list);
	}

	private String createContainer(String mountDir, List<String> commands)
			throws DockerCertificateException, DockerException, InterruptedException {
		Builder configBuilder = HostConfig.builder();
		if (mountDir != null) {
			configBuilder.appendBinds(Bind.from(mountDir).to(MOUNT_DEST).build());
		}
		final HostConfig hostConfig = configBuilder.build();

		String shellScript = "cd " + MOUNT_DEST + ";";
		for (String cmd : commands) {
			shellScript += cmd + ';';
		}
		final ContainerConfig containerConfig = ContainerConfig.builder().hostConfig(hostConfig).image(IMAGE_NAME)
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

	private void buildImage() {
		final AtomicReference<String> imageIdFromMessage = new AtomicReference<>();
		try {
			docker.build(Paths.get(""), IMAGE_NAME, new ProgressHandler() {
				@Override
				public void progress(ProgressMessage message) throws DockerException {
					final String imageId = message.buildImageId();
					if (imageId != null) {
						imageIdFromMessage.set(imageId);
					}
				}
			});
		} catch (DockerException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
}
