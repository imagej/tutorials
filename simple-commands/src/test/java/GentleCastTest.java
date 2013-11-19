import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

public class GentleCastTest {

	@Test
	public void test() throws Exception {
		// get fresh class loader
		ClassLoader loader = TestInterface.class.getClassLoader();
		assertTrue(loader instanceof URLClassLoader);
		URL[] urls = ((URLClassLoader)loader).getURLs();
		ClassLoader parent = loader.getParent();
		for (;;) {
			assertNotNull(parent);
			try {
				assertNotNull(parent.loadClass(TestInterface.class.getName()));
			} catch (ClassNotFoundException e) {
				break;
			}
			parent = parent.getParent();
		}
		loader = new URLClassLoader(urls, parent);

		Class<?> clazz = loader.loadClass(TestClass.class.getName());
		assertNotNull(clazz);
		assertNotSame(clazz, TestClass.class);

		Object instance = clazz.newInstance();
		assertNotNull(instance);
		assertFalse(instance instanceof TestInterface);
		assertEquals(instance.getClass().getClassLoader(), loader);
		Method method = instance.getClass().getMethod("greeting");
		assertEquals(method.invoke(instance), "Hello, Hobbes!");

		TestInterface proxy = GentleCast.cast(instance, TestInterface.class);
		assertNotNull(proxy);
		assertTrue(proxy instanceof TestInterface);
		assertNotSame(proxy.getClass().getClassLoader(), loader);
		assertEquals(proxy.greeting(), "Hello, Hobbes!");
	}

	public interface TestInterface {
		String greeting();
	}

	public static class TestClass implements TestInterface {
		public String greeting() {
			return "Hello, Hobbes!";
		}
	}
}