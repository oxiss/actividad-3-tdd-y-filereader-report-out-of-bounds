package paquete_principal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class Ibex implements Interfaz_Ibex {
	private String path;

	public float getCloseValue(int year, String path) {

		float aux = 0.0F;
		float acumulado = 0.0F;
		String cadena;
		int contador = 0;
		FileReader f = null;
		String yearSt = Integer.toString(year);

		try {
			f = new FileReader(path);
		} catch (FileNotFoundException e) {
			System.err.println("el archivo no existe");
			return -1;
		}
		// creamos un arraylist y comenzamos a leer el archivo
		List<String> datos = new ArrayList<String>();
		BufferedReader bReader = new BufferedReader(f);

		try {
			// añadimos los datos al Arraylist
			while ((cadena = bReader.readLine()) != null) {
				datos.add(cadena);
			}
			bReader.close();
		} catch (IOException e) {

			System.err.println("Error al meter los datos en el Arraylist");
			return -1;
		}
		// recorremos el Arraylist haciendo tokens en los lugares escogidos
		for (String indice : datos) {
			StringTokenizer st = new StringTokenizer(indice, ",");
			// cogemos el tercer token (donde está la fecha)
			for (int i = 0; i < 2; i++) {
				st.nextToken();
			}
			// si el tercer token empieza con el año indicado...
			if (st.nextToken().startsWith(yearSt)) {
				// buscamos el 8º token (cierres)
				for (int i = 0; i < 4; i++) {
					st.nextToken();
				}
				// y lo vamos sumando en acumulado
				aux = Float.parseFloat(st.nextToken());
				acumulado = acumulado + aux;
				// contador con 2 funciones: saber si hay filas para el año dado
				// (!=0) y contar para el divisor
				contador++;
			}
		}
		acumulado = acumulado / contador;
		// System.out.println(acumulado); (prueba para ver las medias)
		// si han existido filas (>1)
		if (contador > 1) {
			return acumulado;
		} else {
			// si no han existido filas (<1)
			System.err.println("la fecha introducida no es correcta");
			return (float) 0.0;
		}

	}

	public ArrayList<Date> getDatePoints(long points, String path) {
		ArrayList<Date> fechas = new ArrayList();
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

	public float getCloseAvg(Date ini, Date fin, String path)
			throws IOException, NumberFormatException, ParseException {
		float media_salida = 0;
		float num_elementos = 0;
		if (ini.after(fin)) {
			System.out
					.println("La fecha de inicio es posterior a la fecha de fin");
			media_salida = -1;
		} else {
			Path archivo = FileSystems.getDefault().getPath(path);
			if (archivo.toFile().isDirectory()) {
				System.out
						.println("La Ruta indicada corresponde a un directorio");
				media_salida = -1;
			} else {
				if (!archivo.getFileName().toString().endsWith(".csv")) {
					System.out.println("No es el tipo de archivo correcto");
					media_salida = -1;
				} else {
					if (archivo.getFileName().toString()
							.equals("bolsaes_^IBEX_20151014.csv")) {
						BufferedReader lector = new BufferedReader(
								new FileReader(path));
						String linea = lector.readLine();
						while ((linea = lector.readLine()) != null) {

							if ((paso_a_fecha(linea.split(",")[2]).compareTo(
									ini) >= 0)
									&& (paso_a_fecha(linea.split(",")[2])
											.compareTo(fin) <= 0)) {
								media_salida += Float
										.valueOf(linea.split(",")[7]);
								num_elementos++;
							}
						}
						lector.close();
					} else {
						System.out.println("No es el archivo correcto.");
						media_salida = -1;
						num_elementos++;
					}
				}
			}
		}
		if (media_salida > 0) {
			media_salida = media_salida / num_elementos;
		}
		return media_salida;
	}

	public float getCloseAvg(Date ini, Date fin) throws NumberFormatException,
			IOException, ParseException {
		if (this.path != null) {
			return getCloseAvg(ini, fin, this.path);
		} else {
			System.out.println("No se ha ingresado la ruta del archivo CSV");
			return -1;
		}

	}

	public float getCloseAvg(String ini, String fin)
			throws NumberFormatException, IOException, ParseException {
		return getCloseAvg(paso_a_fecha(ini), paso_a_fecha(fin), this.path);
	}

	public float getCloseAvg(String ini, String fin, String ruta)
			throws NumberFormatException, IOException, ParseException {
		return getCloseAvg(paso_a_fecha(ini), paso_a_fecha(fin), ruta);
	}

	/**
	 * Metodo que a partir de una cadena de caracteres espedifica se combierte a
	 * la fecha correspondiente
	 * 
	 * @author Bufigol
	 * @param entada
	 *            Un string correspondiente a la fecha con el formato YYYYMMDD
	 * @return La fecha correspondiente al String del parametro de entrada
	 * @throws ParseException
	 */
	private Date paso_a_fecha(String entada) throws ParseException {
		SimpleDateFormat paso_a_fecha = new SimpleDateFormat("YYYYMMDD");
		return paso_a_fecha.parse(entada);
	}

	@Override
	public float getCloseValue(Date fecha, String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getValue(Date fecha, int field, String path) {
		// TODO Auto-generated method stub
		return 0;
	}

}
