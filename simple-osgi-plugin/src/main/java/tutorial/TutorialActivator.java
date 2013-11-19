package tutorial;

import baselib.BaseService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import java.util.logging.Logger;

public class TutorialActivator implements BundleActivator {
	Logger log=Logger.getLogger(this.getClass().getName());

	public void start(BundleContext bc) {
		log.info("started");
		BaseService service = new BaseService();
		bc.registerService(BaseService.class.getName(), service, null);
		service.put("start", "up");
	}

	public void stop(BundleContext bc) {
		log.info("stopped.");
	}
}
