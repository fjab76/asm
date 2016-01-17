package fjab.adapter;

import fjab.asm.ClassTransformerOptimised;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created by franciscoalvarez on 16/01/2016.
 */
public class RemoveNopAdapter extends ClassVisitor {

  public RemoveNopAdapter(ClassVisitor cv){
    super(ASM5, cv);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc,
                                   String signature, String[] exceptions) {

    MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
    if(mv!=null){
      mv = new RemoveNopAdapterMethod(mv);
    }

    return mv;
  }

  private class RemoveNopAdapterMethod extends MethodVisitor{

    public RemoveNopAdapterMethod(MethodVisitor mv){
      super(ASM5,mv);
    }

    public void visitInsn(int opcode){
      System.out.println("inside");
      if(opcode!=NOP){
        super.visitInsn(opcode);
      }
      else{
        System.out.println("nop opcode");
      }
    }
  }

  public static void main(String[] args) throws Exception {

    Path classFile = Paths.get("/Users/franciscoalvarez/MyProjects/asm/myclasses/fjab/overloading/MyClass.class");
    new ClassTransformerOptimised().transformClass(classFile, "fjab/overloading/MyClass.class", RemoveNopAdapter.class);
  }
}
