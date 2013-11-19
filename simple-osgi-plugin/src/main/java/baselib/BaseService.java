package baselib;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class BaseService implements Map<Object, Object> {
	private Logger log = Logger.getLogger(this.getClass().getName());

	public int size() {
		throw new UnsupportedOperationException();
	}

	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}

	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	public Object get(Object key) {
		throw new UnsupportedOperationException();
	}

	public Object put(Object key, Object value) {
		log.info("Putting '" + key + "' => '" + value + "'");
		return value;
	}

	public Object remove(Object key) {
		throw new UnsupportedOperationException();
	}

	public void putAll(Map map) {
		throw new UnsupportedOperationException();
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public Set keySet() {
		throw new UnsupportedOperationException();
	}

	public Collection values() {
		throw new UnsupportedOperationException();
	}

	public Set entrySet() {
		throw new UnsupportedOperationException();
	}
}
