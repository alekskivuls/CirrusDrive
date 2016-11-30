
package cirrus.backend;

import org.springframework.stereotype.Service;

/**
 * Implementation of {@link cirrus.backend.MyBackend}.
 */
@Service
public class MyBackendBean implements MyBackend {

	@Override
	public String adminOnlyEcho(String s) {
		return "admin:" + s;
	}

	@Override
	public String echo(String s) {
		return s;
	}
}
