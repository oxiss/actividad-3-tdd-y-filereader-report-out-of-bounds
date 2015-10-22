package paquete_principal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Ibex {

	public float getCloseValue(int year, String path) {

		
		float aux = 0.0F;
		float acumulado = 0.0F;
		String cadena;
		int contador=0;
		FileReader f = null;
		String yearSt = Integer.toString(year);
		
		try {
			f = new FileReader(path);
		} catch (FileNotFoundException e) {
			System.err.println("el archivo no existe");
			return -1;
		}
		//creamos un arraylist y comenzamos a leer el archivo
		List<String> datos = new ArrayList<String>();
		BufferedReader bReader = new BufferedReader(f);

		try {
			//añadimos los datos al Arraylist
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
			//cogemos el tercer token (donde está la fecha)
			for (int i = 0; i < 2; i++) {
				st.nextToken();
			}
			// si el tercer token empieza con el año indicado...
			if (st.nextToken().startsWith(yearSt)) {
				// buscamos el 8º token (cierres)
				for (int i = 0; i < 4; i++) {
					st.nextToken();
				}
				//y lo vamos sumando en acumulado
				aux = Float.parseFloat(st.nextToken());
				acumulado = acumulado + aux;
				//contador con 2 funciones: saber si hay filas para el año dado (!=0) y contar para el divisor
				contador++;
			}
		}
		acumulado = acumulado/contador;
		//System.out.println(acumulado); (prueba para ver las medias)
		// si han existido filas (>1)
		if (contador>1){
			return acumulado;
		}
		else{
			// si no han existido filas (<1) 
			System.err.println("la fecha introducida no es correcta");
			return (float) 0.0;
		}
		
	}
}

