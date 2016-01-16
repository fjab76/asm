package fjab.adapter.overloading;

import fjab.asm.ClassTransformer;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created by franciscoalvarez on 16/01/2016.
 */
public class DuplicateMethodAdapter extends ClassVisitor {

  public DuplicateMethodAdapter(ClassVisitor cv){
    super(ASM5,cv);
  }

  @Override
  public void visitEnd() {

    MethodVisitor mv = cv.visitMethod(ACC_PUBLIC+ACC_STATIC,"print","()Ljava/lang/Integer;",null,null);
    mv.visitCode();
    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    mv.visitLdcInsn("hello,Integer");
    mv.visitMethodInsn(INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false);
    mv.visitInsn(ACONST_NULL);
    mv.visitInsn(ARETURN);
    mv.visitMaxs(2, 0);
    mv.visitEnd();

    super.visitEnd();
  }

  public static void main(String[] args) throws Exception {

    Path classFile = Paths.get("/Users/franciscoalvarez/MyProjects/asm/myclasses/fjab/overloading/MyClass.class");
    new ClassTransformer().transformClass(classFile, "fjab/overloading/MyClass.class", DuplicateMethodAdapter.class);
  }
}
