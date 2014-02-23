package jframe;

/**
 * Clase Raton
 *
 * @author Antonio Mejorado
 * @version 1.00 2008/6/13
 */
import java.awt.Image;

public class Malo extends Base {

	/**
	 * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto raton.
	 * @param posY es el <code>posiscion en y</code> del objeto raton.
	 * @param image es la <code>imagen</code> del objeto raton.
	 */
    
         private static int conteo = 0;
         
         public static int getConteo()
         {
             return conteo;
         }
         
         public static void setConteo(int a)
         {
             conteo = a;
         }
         
	public Malo(int posX,int posY,Image image){
		super(posX,posY,image);
	}
}

