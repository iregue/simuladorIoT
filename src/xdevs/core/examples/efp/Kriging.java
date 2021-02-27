package xdevs.core.examples.efp;

import java.util.ArrayList; // import the ArrayList class
import java.util.Collections;
import org.nd4j.linalg.api.ops.random.impl.Linspace;

public class Kriging {
	
	static ArrayList<Double> x_list = new ArrayList<Double>(); // Create an ArrayList object
	static ArrayList<Double> y_list = new ArrayList<Double>(); // Create an ArrayList object
	static ArrayList<Double> valores = new ArrayList<Double>(); // Create an ArrayList object
	
	static ArrayList<Double> hh = new ArrayList<Double>(); // Create an ArrayList object
	static ArrayList<Double> vv = new ArrayList<Double>(); // Create an ArrayList object

	public static void distancia(ArrayList<Double> x, ArrayList<Double> y, ArrayList<Double> valores) {
		
		for (int i=0; i < x.size();i++) {
			for(int j=1; j < x.size(); j++) {
				hh.add(Math.sqrt( Math.pow((x.get(i) - x.get(j)),2) + Math.pow((y.get(i) - y.get(j)), 2) ));
				vv.add(0.5 * Math.pow((valores.get(i) - valores.get(j)), 2));

				
			}
		}
		Collections.sort(hh);
		Collections.sort(vv);
		
		regresion_esferica(hh, vv);
	}
	
	public static double variograma(Double c0, Double c, int a, double h) {
		if(h<a) {
			return c0 + c * (1.5 *(h/a) - 0.5 * Math.pow((h/a), 3));
		}
		return c0+c;
		
	}
	
	public static void regresion_esferica(ArrayList<Double> h, ArrayList<Double> v) {
		int  N = 2; // Indices de la Meseta
		double c0 = v.get(0);
		int cmax = v.size() - N;
		double c = cmax - c0;
		int a = h.size() - N;
		
		/*
		Linspace haux = new Linspace(0, h.size()-1,100);
		System.out.println(haux.z().data().getNumber(2));
		
		ArrayList<Double> vaux = new ArrayList<Double>();
		for(int i = 0; i < haux.z().length(); i++) {
			Double variogram = variograma(c0, c, a, (float)haux.z().data().getNumber(i));
			vaux.add(variogram);
		}
		System.out.println(vaux);
		*/
		System.out.println(x_list.size());
		double [][] A = new double[x_list.size()+1][x_list.size()+1];
		for(int i=0; i<x_list.size(); i++) {
			for(int j=0; j<x_list.size(); j++) {
				if(i==j) {
					A[i][j] = 0;
				}
				else {
					A[i][j] = variograma(c0, c, a, Math.sqrt( Math.pow(x_list.get(i) - x_list.get(j), 2) + Math.pow(y_list.get(i) - y_list.get(j), 2)));
				}
			}
		}
		A[x_list.size()][x_list.size()] = 0;
		System.out.println("valores introducidos:");
        for (int i = 0; i < A.length; i++) { 
            for (int j = 0; j < A[i].length; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		x_list.add(2.33);
		x_list.add(1.80);
		x_list.add(-4.0);
		x_list.add(2.53);
		x_list.add(6.5);
		x_list.add(8.11);
		x_list.add(3.5);
		x_list.add(-2.9);
		x_list.add(-1.1);
		x_list.add(7.3);

		y_list.add(1.2);
		y_list.add(-1.1);
		y_list.add(2.9);
		y_list.add(3.5);
		y_list.add(8.11);
		y_list.add(-6.5);
		y_list.add(-2.53);
		y_list.add(-4.0);
		y_list.add(1.80);
		y_list.add(-2.33);
		
		valores.add(30.1);
		valores.add(13.7);
		valores.add(28.6);
		valores.add(19.3);
		valores.add(54.2);
		valores.add(37.8);
		valores.add(61.1);
		valores.add(39.8);
		valores.add(62.9);
		valores.add(16.0);

		distancia(x_list, y_list, valores);
	}

	
	
}
