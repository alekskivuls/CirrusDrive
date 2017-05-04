package cirrus.docker;

public enum Language {

	JAVA("java", "javac -encoding UTF-8 -sourcepath . -d . ", "java "), CPP("cpp", "g++", "./a.out"), PYTHON("py",
			"python3 -m compileall", "python3");

	private final String extension, buildCmd, runCmd;

	private Language(final String extension, final String buildCmd, final String runCmd) {
		this.extension = extension;
		this.buildCmd = buildCmd;
		this.runCmd = runCmd;
	}

	public String getExtension() {
		return extension;
	}

	public static Language fromExtension(String extension) {
		for (Language lang : Language.values()) {
			if (lang.extension.equals(extension)) {
				return lang;
			}
		}
		return null;
	}

	public String getBuildCmd() {
		return buildCmd;
	}

	public String getRunCmd() {
		return runCmd;
	}
}
