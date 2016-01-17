package fjab.asm;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by franciscoalvarez on 09/01/2016.
 */
public class ClassTransformerOptimised {

  /**
   * This method transforms the class specified by 'className' according to the logic implemented by
   * 'classAdapter'. The resulting class is saved in the class file specified by 'classFile'.
   * Normally, b1 is fully parsed and the corresponding events are used to construct b2 from scratch,
   * which is not very efficient. It is much more efficient to copy the parts of b1 that are not transformed directly
   * into b2, without parsing these parts and without generating the corresponding events.
   * This optimization is performed by the ClassReader and ClassWriter components if they have a reference to each
   * other. This optimization is recommended only for “additive” transformations.
   *
   * @param classFile Path of the class file where the transformed bytecode must be saved
   * @param className Internal name of the class. The internal name of a class is just the
   *                  fully qualified name of this class, where dots are replaced with slashes (like java/lang/System).
   *                  The class must be found in the classpath.
   * @param classAdapter Class representing the type of the adapter used to make the transformations. The type must be
   *                     a subclass of org.objectweb.asm.ClassVisitor
   * @throws Exception
   */
  public void transformClass(Path classFile, String className, Class<? extends ClassVisitor> classAdapter) throws Exception{

    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(className);
    byte[] b1 = IOUtils.toByteArray(is);

    ClassReader cr = new ClassReader(b1);
    ClassWriter cw = new ClassWriter(cr, 0);

    TraceClassVisitor traceClassVisitor = new TraceClassVisitor(cw,new PrintWriter(System.out,true));
    CheckClassAdapter checkClassAdapter = new CheckClassAdapter(traceClassVisitor);

    Constructor<? extends ClassVisitor> constructor = classAdapter.getConstructor(ClassVisitor.class);
    ClassVisitor adapter = constructor.newInstance(checkClassAdapter);

    cr.accept(adapter, 0);

    byte[] b2 = cw.toByteArray();
    Files.write(classFile, b2);
  }
}
