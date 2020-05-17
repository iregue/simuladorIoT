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

import java.util.ArrayList;
import java.util.List;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import xdevs.core.modeling.Atomic;
import xdevs.core.modeling.Input;
import xdevs.core.modeling.Port;

/**
 *
 * @author jlrisco
 */
public class SimuladorIoT extends Atomic {

    protected Port<Input> iInAp1 = new Port<>("iInAp1");
    protected Port<Input> iInAp5 = new Port<>("iInAp5");
    protected Port<Input> iInAp6 = new Port<>("iInAp6");
    protected Port<Input> iInAp7 = new Port<>("iInAp7");
    protected Port<Input> iInDh1 = new Port<>("iInDh1");
    protected Port<Input> iInDh2 = new Port<>("iInDh2");
    protected Port<Input> iInDh3 = new Port<>("iInDh3");
    protected Port<Input> iInDh4 = new Port<>("iInDh4");
    protected Port<Input> iInDh5 = new Port<>("iInDh5");
    protected Port<Input> iInDh6 = new Port<>("iInDh6");
    protected Port<Input> iInDh7 = new Port<>("iInDh7");
    protected Port<Input> iInDh8 = new Port<>("iInDh8");
    protected Port<Input> iInDh9 = new Port<>("iInDh9");
    protected Port<Input> iInDh10 = new Port<>("iInDh10");

    protected Port<Input> oOut = new Port<>("oOut");
    protected Input currentInput = null;
    protected double processingTime;
    protected ArrayList<Input> listaInputs = new ArrayList<Input>();
    protected int contadorArray = 0;
    protected int contadorPrint = 0;
    protected File outputFile;
    public SimuladorIoT(String name, double processingTime) {
        super(name);
        super.addInPort(iInAp1);
        super.addInPort(iInAp5);
        super.addInPort(iInAp6);
        super.addInPort(iInAp7);
        super.addInPort(iInDh1);
        super.addInPort(iInDh2);
        super.addInPort(iInDh3);
        super.addInPort(iInDh4);
        super.addInPort(iInDh5);
        super.addInPort(iInDh6);
        super.addInPort(iInDh7);
        super.addInPort(iInDh8);
        super.addInPort(iInDh9);
        super.addInPort(iInDh10);

        super.addOutPort(oOut);
        this.processingTime = processingTime;
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
    public SimuladorIoT(Element xmlAtomic) {
        super(xmlAtomic);
        iIn = (Port<Input>) super.getInPort(iIn.getName());
        oOut = (Port<Input>) super.getOutPort(oOut.getName());
        NodeList xmlParameters = xmlAtomic.getElementsByTagName("parameter");
        Element xmlParameter = (Element)xmlParameters.item(0);
        processingTime = Double.valueOf(xmlParameter.getAttribute("value"));
    }
*/
    @Override
    public void initialize() {
        super.passivate();
    }

    @Override
    public void exit() {
    }

    @Override
    public void deltint() {
    	List<Input> outliers = new ArrayList<Input>();
    	if(contadorArray >= 300) {
        	//listaInputs.add(new Input("example",1000.0,"dh5"));
        	//listaInputs.add(new Input("example",500.0,"dh5"));
        	//listaInputs.add(new Input("example",300.0,"dh5"));

    		try {
    			//System.out.println(listaInputs.get(0).toString());
            	outliers = getOutliers(listaInputs);
            	try {
            	      FileWriter myWriter = new FileWriter("output.txt");
            	      myWriter.write(outliers.toString());
            	      myWriter.close();
            	      //System.out.println("Successfully wrote to the file.");
            	    } catch (IOException e) {
            	      System.out.println("An error occurred.");
            	      e.printStackTrace();
            	    }
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}

            //System.out.println("Lista outliers: " + outliers);


            contadorArray=0;
            contadorPrint++;
        }
        super.passivate();
    }

    @Override
    public void deltext(double e) {
        if (super.phaseIs("passive")) {
            currentInput = iInAp1.getSingleValue();
            processInput(currentInput);

            /*
            if(currentInput != null) {
	            System.out.println("Input recibido" + currentInput.toString());
	            listaInputs.add(contadorArray,currentInput);
	            contadorArray++;
            }
            */
            currentInput = iInAp5.getSingleValue();
            processInput(currentInput);
            currentInput = iInAp6.getSingleValue();
            processInput(currentInput);
            currentInput = iInAp7.getSingleValue();
            processInput(currentInput);
            currentInput = iInDh1.getSingleValue();
            processInput(currentInput);
            currentInput = iInDh2.getSingleValue();
            processInput(currentInput);
            currentInput = iInDh3.getSingleValue();
            processInput(currentInput);
            currentInput = iInDh4.getSingleValue();
            processInput(currentInput);
            currentInput = iInDh5.getSingleValue();
            processInput(currentInput);
            currentInput = iInDh6.getSingleValue();
            processInput(currentInput);
            currentInput = iInDh7.getSingleValue();
            processInput(currentInput);
            currentInput = iInDh8.getSingleValue();
            processInput(currentInput);
            currentInput = iInDh9.getSingleValue();
            processInput(currentInput);
            currentInput = iInDh10.getSingleValue();
            processInput(currentInput);
            super.holdIn("active", processingTime);
        }
    }

    private void processInput(Input currentInput) {
        if(currentInput != null) {
        	//System.out.println("Input recibido" + currentInput.toString());
            listaInputs.add(contadorArray,currentInput);
            contadorArray++;
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
            data2 = input.subList(input.size() / 2, input.size());
        }
        else {
            data1 = input.subList(0, input.size() / 2);
            data2 = input.subList(input.size() / 2 + 1, input.size());
        }
        double q1 = getMedian(data1);
        double q3 = getMedian(data2);
        double iqr = q3 - q1;
        double lowerFence = q1 - 1.5 * iqr;
        double upperFence = q3 + 1.5 * iqr;
        for (int i = 0; i < input.size(); i++) {
        	//System.out.println(input.get(i).toString());
            if (input.get(i).getRadiacion() < lowerFence || input.get(i).getRadiacion() > upperFence)
                output.add(input.get(i));
        }
        return output;
    }
    
    private static double getMedian(List<Input> data) {
        if (data.size() % 2 == 0)
            return (data.get(data.size() / 2).getRadiacion() + data.get(data.size() / 2 - 1).getRadiacion()) / 2;
        else
            return data.get(data.size() / 2).getRadiacion();
    }
}
