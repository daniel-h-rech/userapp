package rech.haeser.daniel;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.mongodb.morphia.Morphia;

/**
 * Morphia map-by-package methods do not work in Wildfly due to the way it handles jar/war/ear resources
 * using a virtual file system (jboss-vfs). This subclass fixes the issue.
 * <br><br>
 * TODO propose change to the morphia team
 *
 * @author daniel.rech
 */
public class WildflyVFSCompatibleMorphia extends Morphia {

    public static final String CLASS_SUFFIX = ".class";

    @Override
    public synchronized Morphia mapPackage(final String packageName, final boolean ignoreInvalidClasses) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Enumeration<URL> resources = classLoader.getResources(packageName.replace('.', '/'));

            while (resources.hasMoreElements()) {
                final URL url = resources.nextElement();
                final JarInputStream inputStream = (JarInputStream) url.openStream();

                JarEntry entry;
                while ((entry = inputStream.getNextJarEntry()) != null) {
                    if (entry.getName().endsWith(CLASS_SUFFIX)) {
                        final String className = packageName + "." + entry.getName().substring(0, entry.getName().length() - CLASS_SUFFIX.length()).replace('/', '.');
                        map(Class.forName(className));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error trying to map package " + packageName);
        }
        return this;
    }
}
