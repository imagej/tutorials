/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the Unlicense for details:
 *     https://unlicense.org/
 */

package howto.app;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * How to list all JARs in the current classpath
 *
 * @author Deborah Schmidt
 */
public class GetJARsInClassPath {

	private static void run() {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		for(URL url: ((URLClassLoader)cl).getURLs()){
			System.out.println(url.getPath());
		}
	}

	public static void main(String...args) {
		run();
	}

}
