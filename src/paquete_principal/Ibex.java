package paquete_principal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ibex implements Interfaz_Ibex {
	/**
	 * Metodo que a partir de una cadena de caracteres espedifica se combierte a
	 * la fecha correspondiente
	 * 
	 * @author Bufigol
	 * @param entada
	 *            Un string correspondiente a la fecha con el formato YYYYMMDD
	 * @return La fecha correspondiente al String del parametro de entrada
	 */
	private Date paso_a_fecha(String entada) {
		SimpleDateFormat paso_a_fecha = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return paso_a_fecha.parse(entada.substring(6, 8) + "/"
					+ entada.substring(4, 6) + "/" + entada.substring(0, 4));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Se dio un error al entrar la fecha");
			e.printStackTrace();
			return null;
		}
	}

}
