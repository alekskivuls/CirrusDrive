package cirrus.docker;

public enum Language {

	JAVA("java"), CPP("cpp"), PYTHON("py");

	private final String extension;

	private Language(final String extension) {
		this.extension = extension;
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
}