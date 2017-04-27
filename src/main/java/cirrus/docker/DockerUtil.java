package cirrus.docker;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DockerUtil {
	public static String createTmpSrcDir(List<String> srcContents, Language lang) {
		File temp = null;
		try {
			temp = File.createTempFile("CD" + System.currentTimeMillis(), "");
			temp.delete();
			temp.mkdir();
			for (int i = 0; i < srcContents.size(); i++) {
				try (PrintWriter out = new PrintWriter(temp.getAbsolutePath() + "/" + i + "." + lang.getExtension())) {
					out.println(srcContents.get(i));
					out.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp != null ? temp.toString() : null;
	}
}
