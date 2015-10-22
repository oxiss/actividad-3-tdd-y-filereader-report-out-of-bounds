package paquete_principal;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;



public class Test_metodos {
	private String ruta_archivo = "/home/felipev/Descargas/bolsaes_^IBEX_20151014.csv";
	private Date fecha_mal_inicio = new Date(0, 0, 0);
	private Date fecha_mal_final = new Date(1, 1, 1);
	private Date fecha_existe_inicio = new Date(2004, 04, 30);
	private Date fecha_existe_final = new Date(2004, 9, 21);
	private String ruta_no_existe = ".";

	/*
	 * Crear mediante un método que recibe la ruta de un fichero de texto CSV y
	 * devuelve en float la media de cierre de la bolsa de dos fechas dadas, si
	 * la fecha no existe devolverá 0.0, si ocurriera algún error devolverá -1
	 * float getCloseAvg(Date ini, Date fin, String path)
	 */
	/**
	 * Test que va a probar el metodo 5 ingresando dos fechas que se sabe que no
	 * van a estar en el fichero con los datos.
	 */
	@Test
	public void test_RangoNoEncontrado() {
		Ibex pruebas = new Ibex();
		try {
			assertEquals("Fecha Inicial no existe", 0.0f, pruebas.getCloseAvg(
					this.fecha_mal_inicio, this.fecha_mal_final, ruta_archivo),
					0.0f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Test para pobrar que se devuelve un error cuando se ingresa una fecha de
	 * inicio posterior a la fecha de fin
	 */
	@Test
	public void test_FechasMalIngresadas() {
		Ibex pruebas = new Ibex();
		try {
			assertEquals("Fechas Mal Ingresadas.", -1f, pruebas.getCloseAvg(
					this.fecha_existe_final, this.fecha_existe_inicio,
					this.ruta_archivo), 0.0f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Metod que se prueba si la ruta ingresada no es la correcta.
	 */
	@Test
	public void test_Ruta_Equivocada() {
		Ibex pruebas = new Ibex();
		try {
			assertEquals("Ruta Equivocada", -1f, pruebas.getCloseAvg(
					this.fecha_existe_final, this.fecha_existe_inicio,
					this.ruta_no_existe), 0.0f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void test_NoEsArchivo() {
		Ibex pruebas = new Ibex();
		try {
			assertEquals("La ruta indicada no es un archivo", -1f,
					pruebas.getCloseAvg(this.fecha_existe_final,
							this.fecha_existe_inicio, this.ruta_no_existe),
					0.0f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
