package fjab.asm;

import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created by franciscoalvarez on 09/01/2016.
 */
public class ClassGenerator {

  public static byte[] generateClass(){

    ClassWriter cw = new ClassWriter(0);
    cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
            "fjab/asm/Frankenstein", null, "java/lang/Object",
            null);
    cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
            null, new Integer(-1)).visitEnd();
    cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
            null, new Integer(0)).visitEnd();
    cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
            null, new Integer(1)).visitEnd();
    cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
            "(Ljava/lang/Object;)I", null, null).visitEnd();
    cw.visitEnd();
    return cw.toByteArray();
  }

  public static void main(String[] args) throws IOException {
    Path path = Paths.get("/Users/franciscoalvarez/MyProjects/javalab/myclasses/fjab/asm/Frankenstein.class");
    byte[] bytecode = ClassGenerator.generateClass();
    Files.write(path, bytecode);
  }
}
