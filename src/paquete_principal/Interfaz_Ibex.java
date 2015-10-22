package paquete_principal;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public interface Interfaz_Ibex {
	
	public float getCloseValue(Date fecha, String path);// Punto 2
	
	public float getCloseValue(int year, String path);// Punto 3
	
	float getValue(Date fecha, int field, String path );//Punto 4
	
	//Punto 5 vvvvvvv

	public float getCloseAvg(Date ini, Date fin, String path)
			throws IOException, NumberFormatException, ParseException;

	public float getCloseAvg(Date ini, Date fin) throws NumberFormatException,
			IOException, ParseException;

	public float getCloseAvg(String ini, String fin)
			throws NumberFormatException, IOException, ParseException;

	public float getCloseAvg(String ini, String fin, String ruta)
			throws NumberFormatException, IOException, ParseException;

	//^^^^^^^^
	
	
	public ArrayList<Date> getDatePoints(long points, String path);//Punto 6

	
}
