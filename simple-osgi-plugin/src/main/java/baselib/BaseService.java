package baselib;

import java.util.logging.Logger;

public class BaseService {
	Logger log=Logger.getLogger(this.getClass().getName());

	public void sayHello() {
		log.info("Hello, world!");
	}
}
