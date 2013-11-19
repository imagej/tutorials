package baselib;

import java.util.logging.Logger;

public class BaseService implements Runnable {
	Logger log=Logger.getLogger(this.getClass().getName());

	public void run() {
		log.info("Hello, world!");
	}
}
