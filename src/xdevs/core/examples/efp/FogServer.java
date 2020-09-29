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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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

// ANSIBLE PORTINIT
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

    protected Port<Input> oOut = new Port<>("oOut");

    protected double processingTime;
    protected ArrayList<Input> listaInputs = new ArrayList<Input>();
    protected int contadorArray = 0;
    protected int contadorPrint = 0;
    protected File outputFile;

    public FogServer(String name, double processingTime) {
        super(name);
        super.addInPort(iArrived);
// ANSIBLE CONSTRUCTOR
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
    	File file = new File("output.txt");
	    /*
    	if(contadorArray >= 100) {

    		try {
    			//System.out.println("ListaInputs" + listaInputs.toString());
            	//outliers = getOutliers(listaInputs);
    			//System.out.println("outliers" + outliers.toString());

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
            	listaInputs.clear();
            	outliers.clear();
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
            contadorArray=0;
            contadorPrint++;
        }
        */
        super.passivate();
        
    }

    @Override
    public void deltext(double e) {
    	if (super.phaseIs("passive")) {
        	
        	currentInput = iInNodoVirtual1.getSingleValue();
        	if(currentInput != null) {
            	System.out.println("FogServer: " + currentInput.toString());

        	}
        	
        	processInput(currentInput);

            super.passivate();
            super.holdIn("active", 0);
        }
    }

    @Override
    public void lambda() {
        oOut.addValue(currentInput);
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
}
