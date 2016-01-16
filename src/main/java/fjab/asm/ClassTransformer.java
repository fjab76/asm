package fjab.asm;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by franciscoalvarez on 09/01/2016.
 */
public class ClassTransformer {

  /**
   * This method transforms the class specified by 'className' according to the logic implemented by
   * 'classAdapter'. The resulting class is saved in the class file specified by 'classFile'
   * @param classFile Path of the class file where the transformed bytecode must be saved
   * @param className Fully qualified name of the class following JVM convention, that is, using slash ('/')
   *                  to separate the different parts of the class name (like java/lang/System). The class must be found
   *                  in the classpath.
   * @param classAdapter Class representing the type of the adapter used to make the transformations. The type must be
   *                     a subclass of org.objectweb.asm.ClassVisitor
   * @throws Exception
   */
  public void transformClass(Path classFile, String className, Class<? extends ClassVisitor> classAdapter) throws Exception{

    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(className);
    byte[] b1 = IOUtils.toByteArray(is);
    
    ClassReader cr = new ClassReader(b1);
    ClassWriter cw = new ClassWriter(cr, 0);

    Constructor<? extends ClassVisitor> constructor = classAdapter.getConstructor(ClassVisitor.class);
    ClassVisitor adapter = constructor.newInstance(cw);

    cr.accept(adapter, 0);

    byte[] b2 = cw.toByteArray();
    Files.write(classFile, b2);
  }
}
