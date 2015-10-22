package paquete_principal;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ibex implements Interfaz_Ibex {
	private String path;

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
