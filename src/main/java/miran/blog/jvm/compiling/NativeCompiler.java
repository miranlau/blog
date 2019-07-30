package miran.blog.jvm.compiling;

import javax.tools.*;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Locale;

public class NativeCompiler implements Closeable {
    private final File tempFolder;
    private final URLClassLoader classLoader;

    public NativeCompiler(File tempFolder) {
        this.tempFolder = tempFolder;
        this.classLoader = createClassLoader(tempFolder);
    }

    private static URLClassLoader createClassLoader(File tempFolder) {
        try {
            URL[] urls = {tempFolder.toURI().toURL()};
            return new URLClassLoader(urls);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public Class<?> compile(String className, String code) {
        try {
            JavaFileObject sourceFile = new StringJavaFileObject(className, code);
            compileClass(sourceFile);
            return classLoader.loadClass(className);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private void compileClass(JavaFileObject sourceFile) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = null;

        try {
            fileManager = compiler.getStandardFileManager(collector, Locale.ROOT, null);
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(tempFolder));
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, collector, null, null, Arrays.asList(sourceFile));
            task.call();
        } finally {
            fileManager.close();
        }
    }

    @Override
    public void close() {
        try {
            classLoader.close();
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public URLClassLoader getClassLoader() {
        return classLoader;
    }
}
