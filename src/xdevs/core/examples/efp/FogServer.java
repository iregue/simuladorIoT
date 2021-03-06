/*
 * Copyright (C) 2014-2015 José Luis Risco Martín <jlrisco@ucm.es> and 
 * Saurabh Mittal <smittal@duniptech.com>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, see
 * http://www.gnu.org/licenses/
 *
 * Contributors:
 *  - José Luis Risco Martín
 */
package xdevs.core.examples.efp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Jama.Matrix;
import smile.interpolation.CubicSplineInterpolation1D;
import smile.interpolation.RBFInterpolation1D;
import smile.interpolation.ShepardInterpolation1D;
import smile.math.rbf.GaussianRadialBasis;
import xdevs.core.modeling.Atomic;
import xdevs.core.modeling.Input;
import xdevs.core.modeling.Port;

/**
 *
 * @author José Luis Risco Martín TODO: I keep the Transducer atomic model for
 * the end ...
 */
public class FogServer extends Atomic {

    private static final Logger LOGGER = Logger.getLogger(FogServer.class.getName());

    protected Port<Input> iArrived = new Port<>("iArrived");
    protected Port<Input> iInNodoVirtual1 = new Port<>("iInNodoVirtual1");
    protected Port<Input> iInNodoVirtual2 = new Port<>("iInNodoVirtual2");
    protected Port<Input> iInNodoVirtual3 = new Port<>("iInNodoVirtual3");
    protected Port<Input> iInNodoVirtual4 = new Port<>("iInNodoVirtual4");
    protected Port<Input> iInNodoVirtual5 = new Port<>("iInNodoVirtual5");
    protected Port<Input> iInNodoVirtual6 = new Port<>("iInNodoVirtual6");
    protected Port<Input> iInNodoVirtual7 = new Port<>("iInNodoVirtual7");
    protected Port<Input> iInNodoVirtual8 = new Port<>("iInNodoVirtual8");
    protected Port<Input> iInNodoVirtual9 = new Port<>("iInNodoVirtual9");
    protected Port<Input> iInNodoVirtual10 = new Port<>("iInNodoVirtual10");
    protected Port<Input> iInNodoVirtual11 = new Port<>("iInNodoVirtual11");
    protected Port<Input> iInNodoVirtual12 = new Port<>("iInNodoVirtual12");
    protected Port<Input> iInNodoVirtual13 = new Port<>("iInNodoVirtual13");
    protected Port<Input> iInNodoVirtual14 = new Port<>("iInNodoVirtual14");
    protected Port<Input> iInNodoVirtual15 = new Port<>("iInNodoVirtual15");
    protected Input currentInput = null;
    protected Input currentInputNodovirtual1 = null;
    protected Input currentInputNodovirtual2 = null;

    protected Port<Input> oOut = new Port<>("oOut");

    protected double processingTime;
    protected ArrayList<Input> listaInputs = new ArrayList<Input>();
    protected int contadorArray = 0;
    protected int contadorPrint = 0;
    protected File outputFile;
    protected Collection<Input> collection = null;
    public static ArrayList<Double> x_list = new ArrayList<Double>(); // Create an ArrayList object
	public static ArrayList<Double> y_list = new ArrayList<Double>(); // Create an ArrayList object
	public static ArrayList<Double> valores = new ArrayList<Double>(); // Create an ArrayList object
	double x0 = 13.0;
	double y0 = 80.0;
	protected int contadorKriging = 0;
	//########################################
	double krigingiInNodoVirtual1 = 0.0;
	double krigingiInNodoVirtual2 = 0.0;
	//########################################
    public FogServer(String name, double processingTime) {
        super(name);
        super.addInPort(iArrived);
        super.addInPort(iInNodoVirtual1);
        super.addInPort(iInNodoVirtual2);
        super.addInPort(iInNodoVirtual3);
        super.addInPort(iInNodoVirtual4);
        super.addInPort(iInNodoVirtual5);
        super.addInPort(iInNodoVirtual6);
        super.addInPort(iInNodoVirtual7);
        super.addInPort(iInNodoVirtual8);
        super.addInPort(iInNodoVirtual9);
        super.addInPort(iInNodoVirtual10);
        super.addInPort(iInNodoVirtual11);
        super.addInPort(iInNodoVirtual12);
        super.addInPort(iInNodoVirtual13);
        super.addInPort(iInNodoVirtual14);
        super.addInPort(iInNodoVirtual15);
        this.processingTime = processingTime;

        super.addOutPort(oOut);
        try {
            outputFile = new File("output.txt");
            if (outputFile.createNewFile()) {
                System.out.println("File created: " + outputFile.getName());
              } else {
                System.out.println("File already exists.");
              }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
      //########################################
        x_list.add(12.771);
        x_list.add(9.692);
        x_list.add(8.165);
        x_list.add(14.779);
        x_list.add(15.409);
        /*
        x_list.add(14.244);
        x_list.add(12.264);
        x_list.add(12.957);
        x_list.add(13.328);
        x_list.add(11.775);
        x_list.add(14.128);
        x_list.add(10.304);
        x_list.add(12.744);
        x_list.add(11.875);
		*/
        y_list.add(83.829);
        y_list.add(82.415);
        y_list.add(79.381);
        y_list.add(77.897);
        y_list.add(86.845);
        /*
        y_list.add(84.642);
        y_list.add(84.802);
        y_list.add(85.018);
        y_list.add(84.354);
        y_list.add(86.837);
        y_list.add(86.738);
        y_list.add(86.650);
        y_list.add(85.513);
        */
      //########################################
    }

    /*
    public FogServer(Element xmlAtomic) {
        super(xmlAtomic);
        iArrived = (Port<Input>) super.getInPort(iArrived.getName());
        iSolved = (Port<Input>) super.getInPort(iSolved.getName());
        oOut = (Port<Input>) super.getOutPort(oOut.getName());  
        NodeList xmlParameters = xmlAtomic.getElementsByTagName("parameter");
        Element xmlParameter = (Element)xmlParameters.item(0);
        totalTa = 0;
        clock = 0;
        observationTime = Double.valueOf(xmlParameter.getAttribute("value"));
    }
	*/
    
    @Override
    public void initialize() {
        //super.holdIn("active", processingTime);
        super.passivate();
    }

    @Override
    public void exit() {
    }

    @Override
    public void deltint() {

    	List<Input> outliers = new ArrayList<Input>();
    	//File file = new File("output.txt");
	    
    	if(contadorArray >= 100) {

    		try {
    			//System.out.println("ListaInputs" + listaInputs.toString());
            	outliers = getOutliers(listaInputs);
    			System.out.println("outliers" + outliers.toString());
    			if(outliers.size() > 0) {
    				try {
    				double[] x = {0, 1, 2, 3, 4, 5, 6};
    			    double[] y = {listaInputs.get(listaInputs.size()-6).getRadiacion(), 
    			    		listaInputs.get(listaInputs.size()-5).getRadiacion(), 
    			    		listaInputs.get(listaInputs.size()-4).getRadiacion(), 
    			    		listaInputs.get(listaInputs.size()-3).getRadiacion(), 
    			    		listaInputs.get(listaInputs.size()-2).getRadiacion(), 
    			    		listaInputs.get(listaInputs.size()-1).getRadiacion(), 
    			    		listaInputs.get(listaInputs.size()).getRadiacion()};
    			    
    			    double[][] controls = new double[x.length][2];
    			       for (int i = 0; i < controls.length; i++) {
    			           controls[i][0] = x[i];
    			           controls[i][1] = y[i];
    			       }
    			       
    			       CubicSplineInterpolation1D spline = new CubicSplineInterpolation1D(x, y);
    			       double[][] zz = new double[61][2];
    			       for (int i = 0; i <= 60; i++) {
    			           zz[i][0] = i * 0.1;
    			           zz[i][1] = spline.interpolate(zz[i][0]);
    			       }
    			       
    			       RBFInterpolation1D rbf = new RBFInterpolation1D(x, y, new GaussianRadialBasis());
    			       double[][] ww = new double[61][2];
    			       for (int i = 0; i <= 60; i++) {
    			           ww[i][0] = i * 0.1;
    			           ww[i][1] = rbf.interpolate(zz[i][0]);
    			       }
    			       
    			       ShepardInterpolation1D shepard = new ShepardInterpolation1D(x, y, 3);
    			       double[][] vv = new double[61][2];
    			       for (int i = 0; i <= 60; i++) {
    			           vv[i][0] = i * 0.1;
    			           vv[i][1] = shepard.interpolate(zz[i][0]);
    			       }
    			       
    			       
    			       Input inputCubicSpline = new Input(listaInputs.get(listaInputs.size()).getDate(), zz[30][1], "CubicSpline");
    			       Input inputRBF = new Input(listaInputs.get(listaInputs.size()).getDate(), ww[30][1], "RBF");
    			       Input inputShepard = new Input(listaInputs.get(listaInputs.size()).getDate(), vv[30][1], "Shepard");

    			       processInput(inputCubicSpline);
    			       processInput(inputRBF);
    			       processInput(inputShepard);

    				}
    				catch(Exception e) {
    					System.out.println(e);
    				}
    			       
    			}
    			/*
        		FileWriter fr = new FileWriter(file, true);
        		BufferedWriter br = new BufferedWriter(fr);
        		PrintWriter pr = new PrintWriter(br);
            	for (int i = 0; i < outliers.size(); i++) {           		
            		pr.println(outliers.get(i).toString());
            	}
            	pr.println("Sep");
            	pr.close();
            	br.close();
            	fr.close();
            	*/
            	listaInputs.clear();
            	outliers.clear();
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
            contadorArray=0;
            contadorPrint++;
        }
        
        super.passivate();
        
    }

    @Override
    public void deltext(double e) {
    	if (super.phaseIs("passive")) {
        	//########################################
    		currentInputNodovirtual1 = iInNodoVirtual1.getSingleValue();
    		processInput(currentInputNodovirtual1);

    		currentInputNodovirtual2 = iInNodoVirtual2.getSingleValue();
    		processInput(currentInputNodovirtual2);
    		
        	if(currentInputNodovirtual1 != null) {
            	System.out.println("FogServer: " + currentInputNodovirtual1.toString());
            	processInput(currentInputNodovirtual1);
            	krigingiInNodoVirtual1 += currentInputNodovirtual1.getRadiacion();
        	}
        	
        	if(currentInputNodovirtual2 != null) {
            	System.out.println("FogServer: " + currentInputNodovirtual2.toString());
            	processInput(currentInputNodovirtual2);
            	krigingiInNodoVirtual2 += currentInputNodovirtual2.getRadiacion();
        	}
        	//########################################
        	contadorKriging++;
        	if(contadorKriging == 100) {
        		Input krigingInput;
        		double valor = 0.0;
        		double valorMedio = 0.0;
        		valores.add(krigingiInNodoVirtual1/100);
        		valores.add(krigingiInNodoVirtual2/100);
        		valores.add(77.0);
        		valores.add(85.0);
        		valores.add(70.0);
        		try {
        			valor = calculateKriging(x_list, y_list, valores, x0, y0);
            		//System.out.println("VALOR KRINGIN:" + valor );
        		}
        		catch(Exception e1) {
        			System.out.println(e1);
        			
        		}
        		double max = valores.get(0);
        		double min = valores.get(0);
        		for (int i= 0; i< valores.size(); i++) {
        			if(valores.get(i) < min) {
        				min = valores.get(i);
        			}
        			
        			if(valores.get(i) > max) {
        				max = valores.get(i);
        			}
        			
        		}
        		if(valor < max && valor > min) {
            		krigingInput = new Input(currentInputNodovirtual1.getDate(), valor, "kriging");
        		}
        		else {
        			//Si el valor no es acorde a lo esperado, reemplazarlo por la media del resto
        			for(int i=0; i < valores.size(); i++) {
        				valorMedio += valores.get(i);
        			}
        			valorMedio = valorMedio / (valores.size()-1); 
            		krigingInput = new Input(currentInputNodovirtual1.getDate(), valorMedio, "krigingCorregido");
        		}
        		processInput(krigingInput);
        		contadorKriging=0;
        	}
            super.passivate();
            super.holdIn("active", 0);
        }
    }

    @Override
    public void lambda() {
    	oOut.addValues(listaInputs);
        //oOut.addValue(currentInput);
    }
    
    
    //Calculo Outliers http://www.mathwords.com/o/outlier.htm
    public static List<Input> getOutliers(List<Input> input) {
        List<Input> output = new ArrayList<Input>();
        List<Input> data1 = new ArrayList<Input>();
        List<Input> data2 = new ArrayList<Input>();
        if (input.size() % 2 == 0) {
            data1 = input.subList(0, input.size() / 2);
            //System.out.println("data1: " + data1.toString());
            data2 = input.subList(input.size() / 2, input.size());
            //System.out.println("data2: " + data2.toString());

        }
        else {
            data1 = input.subList(0, input.size() / 2);
            data2 = input.subList(input.size() / 2 + 1, input.size());
        }
        double q1 = getMedian(data1);
        //System.out.println("q1: " + q1);
        double q3 = getMedian(data2);
        //System.out.println("q3: " + q3);

        double iqr = q3 - q1;       
        if(iqr < 0) {
        	iqr = -iqr;
        }
        //System.out.println("iqr: " + iqr);

        double lowerFence = q1 - 1.5 * iqr;
        //System.out.println("lowerFence: " + lowerFence);

        double upperFence = q3 + 1.5 * iqr;
        //System.out.println("upperFence: " + upperFence);

        for (int i = 0; i < input.size(); i++) {
        	//System.out.println(input.get(i).toString());
            if (input.get(i).getRadiacion() < lowerFence || input.get(i).getRadiacion() > upperFence) {
            	output.add(input.get(i));
                //System.out.println("Oulier: " + input.get(i).toString());
            }
                
        }
        return output;
    }
    
    private static double getMedian(List<Input> data) {
        if (data.size() % 2 == 0)
            return (data.get(data.size() / 2).getRadiacion() + data.get(data.size() / 2 - 1).getRadiacion()) / 2;
        else
            return data.get(data.size() / 2).getRadiacion();
    }
    
    private void processInput(Input currentInput) {
        if(currentInput != null) {
        	//System.out.println("Input recibido" + currentInput.toString());
            listaInputs.add(contadorArray,currentInput);
            contadorArray++;
        }
    }
    
    public static double variograma(Double c0, Double c, int a, double h) {
		if(h<a) {
			return c0 + c * (1.5 *(h/a) - 0.5 * Math.pow((h/a), 3));
		}
		return c0+c;
		
	}
    
    public static double calculateKriging(ArrayList<Double> x, ArrayList<Double> y, ArrayList<Double> valores, double x0, double y0) {
    	
    	ArrayList<Double> hh = new ArrayList<Double>(); // Create an ArrayList object
    	ArrayList<Double> vv = new ArrayList<Double>(); // Create an ArrayList object
    	
    	for (int i=0; i < x.size();i++) {
			for(int j=1; j < x.size(); j++) {
				hh.add(Math.sqrt( Math.pow((x.get(i) - x.get(j)),2) + Math.pow((y.get(i) - y.get(j)), 2) ));
				vv.add(0.5 * Math.pow((valores.get(i) - valores.get(j)), 2));

				
			}
		}
		Collections.sort(hh);
		Collections.sort(vv);
		
		double result = kriging(hh, vv,valores, x0, y0);
		return result;
    }
    
    public static double kriging(ArrayList<Double> h, ArrayList<Double> v, ArrayList<Double> valores, double x0, double y0) {
		
    	
    	double f0 = 0;
    	int  N = 2; // Indices de la Meseta
		double c0 = v.get(0);
		int cmax = v.size() - N;
		double c = cmax - c0;
		int a = h.size() - N;
		
		
		System.out.println(x_list.size());
		double [][] A = new double[x_list.size()+1][x_list.size()+1];
		for(int i=0; i<x_list.size()+1; i++) {
			for(int j=0; j<x_list.size()+1; j++) {
				A[i][j] = 1.0;
			}	
		}
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
		//Print matriz A
		System.out.println("valores introducidos A:");
        for (int i = 0; i < A.length; i++) { 
            for (int j = 0; j < A[i].length; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
        double [][] B = new double[x_list.size()+1][1];
        for(int i=0; i <x_list.size(); i++) {
        	B[i][0] = variograma(c0, c, a, Math.sqrt(Math.pow(x_list.get(i) - x0, 2)+ Math.pow(y_list.get(i) - y0, 2)));
        }
        
        Matrix A_matrix = new Matrix(A);
        Matrix B_matrix = new Matrix(B);
        A_matrix.print(5, 2);
        B_matrix.print(5, 2);
        
        Matrix w = A_matrix.inverse().times(B_matrix);
        w.print(5, 2);
        
        for(int i=0; i<valores.size(); i++) {
        	f0 += w.get(i, 0)*valores.get(i);
        }
        System.out.println("f0= " + f0);
        
        return f0;
    }
    
}
