package paquete_principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	@Override
	public float getCloseValue(Date fecha, String path) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	@Override
	public float getCloseValue(int year, String path) {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}
	
	

	public float getValue(Date fecha, int field, String path ){
		
		BufferedReader br = null;
		String linea = "";
		String [] bolsa = null;
		String salidapantalla = "";
		String stringFecha = dateToString(fecha);
				
		try {
			FileReader fichero = new FileReader(path);
			br = new BufferedReader(fichero);
			while ((linea = br.readLine()) != null) {
				bolsa = linea.split(",");
			}	
			br.close();
			
			if (bolsa[2].equals(stringFecha)){
				if (field== 4){	
					salidapantalla = " El valor de apertura es ";
				}else if (field == 5){
					salidapantalla = " El valor mas alto es ";
				}else if (field == 6){
					salidapantalla = " El valor mas bajo es ";
				}else if (field == 7){
					salidapantalla = " El valor de cierre es ";
				}else
					System.err.println("-1");				
				
				System.out.println("Fecha: " + bolsa[2] + salidapantalla + bolsa[field]);

			}else {
				System.out.println("0,0");
			}

		}catch (IOException e){
			System.err.println("-1");
		}		
		return field;	
	}

	@Override
	public ArrayList<Date> getDatePoints(long points, String path) {
		// TODO Apéndice de método generado automáticamente
		return null;
	}
	
	private static String dateToString(Date fecha) {
		String stringFecha =  null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try{
			stringFecha = sdf.format(fecha);
		}
		catch(Exception e){
		}
		return stringFecha;
	}
}
