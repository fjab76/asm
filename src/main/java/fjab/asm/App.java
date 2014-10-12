package fjab.asm;

import java.io.IOException;

import org.objectweb.asm.ClassReader;


public class App 
{
	private static int j;
	
    public static void main(String[] args) throws IOException
    {
    	ClassPrinter cp = new ClassPrinter();
    	ClassReader cr = new ClassReader("fjab.asm.App");
    	cr.accept(cp, 0);
       
    }
}
