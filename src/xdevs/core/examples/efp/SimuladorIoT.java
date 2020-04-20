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

    protected Port<Input> iIn = new Port<>("iIn");
    protected Port<Input> oOut = new Port<>("oOut");
    protected Input currentInput = null;
    protected double processingTime;
    protected ArrayList<Double> listaInputs = new ArrayList<Double>();

    public SimuladorIoT(String name, double processingTime) {
        super(name);
        super.addInPort(iIn);
        super.addOutPort(oOut);
        this.processingTime = processingTime;
    }

    public SimuladorIoT(Element xmlAtomic) {
        super(xmlAtomic);
        iIn = (Port<Input>) super.getInPort(iIn.getName());
        oOut = (Port<Input>) super.getOutPort(oOut.getName());
        NodeList xmlParameters = xmlAtomic.getElementsByTagName("parameter");
        Element xmlParameter = (Element)xmlParameters.item(0);
        processingTime = Double.valueOf(xmlParameter.getAttribute("value"));
    }

    @Override
    public void initialize() {
        super.passivate();
    }

    @Override
    public void exit() {
    }

    @Override
    public void deltint() {
        super.passivate();
    }

    @Override
    public void deltext(double e) {
        if (super.phaseIs("passive")) {
            currentInput = iIn.getSingleValue();
            super.holdIn("active", processingTime);
            System.out.println("PreCambio" + currentInput.toString());
            listaInputs.add(currentInput.getRadiacion());
            currentInput.setRadiacion(currentInput.getRadiacion() / 2.0);
            
            //System.out.println("PostCambios: "+ currentInput.toString());

        }
    }

    @Override
    public void lambda() {
        oOut.addValue(currentInput);
        if(listaInputs.size() == 30) {
        	listaInputs.add(1000.0);
        	listaInputs.add(10.0);
        	listaInputs.add(500.0);

        	List<Double> outliers = getOutliers(listaInputs);
        	
        	System.out.println("Lista outliers: " + outliers);
        }
    }
    
    public static List<Double> getOutliers(List<Double> input) {
        List<Double> output = new ArrayList<Double>();
        List<Double> data1 = new ArrayList<Double>();
        List<Double> data2 = new ArrayList<Double>();
        if (input.size() % 2 == 0) {
            data1 = input.subList(0, input.size() / 2);
            data2 = input.subList(input.size() / 2, input.size());
        } else {
            data1 = input.subList(0, input.size() / 2);
            data2 = input.subList(input.size() / 2 + 1, input.size());
        }
        double q1 = getMedian(data1);
        double q3 = getMedian(data2);
        double iqr = q3 - q1;
        double lowerFence = q1 - 1.5 * iqr;
        double upperFence = q3 + 1.5 * iqr;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i) < lowerFence || input.get(i) > upperFence)
                output.add(input.get(i));
        }
        return output;
    }
    
    private static double getMedian(List<Double> data) {
        if (data.size() % 2 == 0)
            return (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2;
        else
            return data.get(data.size() / 2);
    }
}
