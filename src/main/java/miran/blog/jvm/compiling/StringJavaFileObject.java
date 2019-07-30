package miran.blog.jvm.compiling;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

public class StringJavaFileObject extends SimpleJavaFileObject {
    private static final String SCHEME = "string:///";
    private final String code;

    protected StringJavaFileObject(String className, String code) {
        super(URI.create(SCHEME + className.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return code;
    }
}
