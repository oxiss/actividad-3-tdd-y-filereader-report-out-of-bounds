package paquete_principal;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
/*
 * prueba de conexion desde eclipse a rama javi
 */
public interface Interfaz_Ibex {
	public float getCloseAvg(Date ini, Date fin, String path)
			throws IOException, NumberFormatException, ParseException;

	public float getCloseAvg(Date ini, Date fin) throws NumberFormatException,
			IOException, ParseException;

	public float getCloseAvg(String ini, String fin)
			throws NumberFormatException, IOException, ParseException;

	public float getCloseAvg(String ini, String fin, String ruta)
			throws NumberFormatException, IOException, ParseException;

	public ArrayList<Date> getDatePoints(long points, String path);

	public float getCloseValue(int year, String path);
}

