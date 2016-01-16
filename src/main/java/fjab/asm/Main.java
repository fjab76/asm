package fjab.asm;

import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by franciscoalvarez on 09/01/2016.
 */
public class Main {

  public static void main(String[] args) throws IOException {
    ClassPrinter cp = new ClassPrinter();
    ClassReader cr = new ClassReader("java.lang.Runnable");
    cr.accept(cp, 0);

    InputStream is = Main.class.getClassLoader().getResourceAsStream("java.lang.Runnable".replace(".", "/") + ".class");
    int i;
    while((i=is.read())!=-1){
      System.out.print(Integer.toHexString(i)+" ");
    }

    //System.out.println("Frankenstein.LESS="+Frankenstein.LESS);
  }
}
