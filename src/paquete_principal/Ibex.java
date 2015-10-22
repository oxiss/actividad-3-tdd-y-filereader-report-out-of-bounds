package paquete_principal;

//
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Ibex implements Interfaz_Ibex {

	public static ArrayList<Date> getDatePoints(long points, String path) {
		ArrayList<Date> fechas = new ArrayList();
		;
		File listaCsv = new File(path);
		try {

			int count = 0;// Counter for evade the title String.
			double close;// Variable where the string of the close data becomes
							// double.
			List<String> lines = Files.readAllLines(listaCsv.toPath(),
					StandardCharsets.UTF_8);// List
											// where
											// all
											// the
											// lines
											// are
											// saved
											// with
											// and
											// UFT_8
											// standard.
			for (String line : lines) {// For any String in the List lines:
				if (count > 0) { // The condition that will evade the title.
					String[] array = line.split(",");// The lines are cutted in
														// the , character and
														// that parts introduced
														// into an array.
					DateFormat format = new SimpleDateFormat("yyyyMMdd");// The
																			// string
																			// date
																			// is
																			// transformed
																			// into
																			// a
																			// date
																			// with
																			// ans
																			// specific
																			// format.
					Date fecha = format.parse(array[2]);// We parse the format
														// of string to the
														// selected one of date.
					close = Double.parseDouble(array[7]);// Transform the value
															// from String to
															// double.
					if (close > points) {// If the double value is higher than
											// the one received as parameter, it
											// will be added to the final array.
						fechas.add(fecha);
					}
				}
				count++;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error en la ruta del archivo.");
		} catch (ParseException e) {
			System.out.println("Error in parsing.");
			e.printStackTrace();
		}
		return fechas;
	}
}