import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.WeakHashMap;

/**
 * Casts between {@link ClassLoader}s.
 * <p>
 * Given an interface, this class casts an object to that interface even if it
 * was loaded in a different {@link ClassLoader}.
 * </p>
 * <p>
 * Example: if an OSGi bundle offers a service that implements a certain
 * interface and we want to use that service outside of the loaded OSGi
 * framework, we cannot simply cast the service returned by the
 * {@code bundleContext.getService(ServiceReference)} method. Instead, we need
 * to construct a {@link Proxy}, which this helper class does.
 * </p>
 * @author Johannes Schindelin
 */
public class GentleCast {
	@SuppressWarnings("unchecked")
	public static<T> T cast(final Object o, Class<T> interfaze) throws ClassNotFoundException {
		if (o == null) {
			return null;
		}
		if (interfaze.isInstance(o)) {
			return interfaze.cast(o);
		}
		ClassLoader clazzLoader = interfaze.getClassLoader();
		ClassLoader oLoader = o.getClass().getClassLoader();
		if (oLoader == clazzLoader) {
			throw new ClassCastException("Cannot cast " + o.getClass().getName() +
				" to " + interfaze.getName());
		}
		final Class<?> otherClass = oLoader.loadClass(interfaze.getName());
		if (!otherClass.isInstance(o)) {
			throw new ClassCastException("Cannot cast " + o.getClass().getName() +
				" to " + otherClass.getName());
		}
		return (T) Proxy.newProxyInstance(clazzLoader, new Class[] { interfaze },
				new InvocationHandler() {
			private final MethodMap methods = new MethodMap(otherClass);

			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable
			{
				return methods.get(method).invoke(o, args);
			}
		});
	}

	private static class MethodMap extends WeakHashMap<Method, Method> {
		private final Class<?> otherClass;

		public MethodMap(Class<?> otherClass) {
			this.otherClass = otherClass;
		}

		@Override
		public Method get(Object key) {
			Method result = super.get(key);
			if (result != null) {
				return result;
			}
			Method method = (Method) key;
			try {
				result = otherClass.getMethod(method.getName(),
					method.getParameterTypes());
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
			put(method, result);
			return result;
		}
	}
}
