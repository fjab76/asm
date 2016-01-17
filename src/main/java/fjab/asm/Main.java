package fjab.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by franciscoalvarez on 09/01/2016.
 */
public class Main {

  public static void main(String[] args) throws Exception {
    ClassPrinter cp = new ClassPrinter();
    PrintWriter printWriter = new PrintWriter(new StringWriter());
    TraceClassVisitor traceClassVisitor = new TraceClassVisitor(cp,printWriter);
    ClassReader cr = new ClassReader("java.lang.Runnable");
    cr.accept(cp, 0);
    //printWriter.toString();

    InputStream is = Main.class.getClassLoader().getResourceAsStream("java.lang.Runnable".replace(".", "/") + ".class");
    int i;
    while((i=is.read())!=-1){
      System.out.print(Integer.toHexString(i)+" ");
    }

    //System.out.println("Frankenstein.LESS="+Frankenstein.LESS);

    Textifier.main(new String[]{"java.lang.Runnable"});
    System.out.println("==========================================");
    ASMifier.main(new String[]{"fjab.overloading.MyClass"});
  }
}
