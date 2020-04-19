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

import java.util.LinkedList;
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
public class Transducer extends Atomic {

    private static final Logger LOGGER = Logger.getLogger(Transducer.class.getName());

    protected Port<Input> iArrived = new Port<>("iArrived");
    protected Port<Input> iSolved = new Port<>("iSolved");
    protected Port<Input> oOut = new Port<>("oOut");

    protected LinkedList<Input> jobsArrived = new LinkedList<>();
    protected LinkedList<Input> jobsSolved = new LinkedList<>();
    protected double observationTime;
    protected double totalTa;
    protected double clock;

    public Transducer(String name, double observationTime) {
        super(name);
        super.addInPort(iArrived);
        super.addInPort(iSolved);
        super.addOutPort(oOut);
        totalTa = 0;
        clock = 0;
        this.observationTime = observationTime;
    }

    public Transducer(Element xmlAtomic) {
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

    @Override
    public void initialize() {
        super.holdIn("active", observationTime);
    }

    @Override
    public void exit() {
    }

    @Override
    public void deltint() {
        clock = clock + getSigma();
        double throughput;
        double avgTaTime;
        if (phaseIs("active")) {
            if (!jobsSolved.isEmpty()) {
                avgTaTime = totalTa / jobsSolved.size();
                if (clock > 0.0) {
                    throughput = jobsSolved.size() / clock;
                } else {
                    throughput = 0.0;
                }
            } else {
                avgTaTime = 0.0;
                throughput = 0.0;
            }
            LOGGER.info("End time: " + clock);
            LOGGER.info("Jobs arrived : " + jobsArrived.size());
            LOGGER.info("Jobs solved : " + jobsSolved.size());
            LOGGER.info("Average TA = " + avgTaTime);
            LOGGER.info("Throughput = " + throughput);
            holdIn("done", 0);
        } else {
            passivate();
        }
        //logger.info("####deltint: "+showState());
    }

    @Override
    public void deltext(double e) {
        clock = clock + e;
        if (phaseIs("active")) {
            Input input = null;
            if (!iArrived.isEmpty()) {
            	input = iArrived.getSingleValue();
                LOGGER.fine("Start job " + input.getRadiacion() + " @ t = " + clock);
                input.setRadiacion(clock);
                jobsArrived.add(input);
            }
            if (!iSolved.isEmpty()) {
            	input = iSolved.getSingleValue();
                totalTa += (clock + input.getRadiacion());
                LOGGER.fine("Finish job " + input.getRadiacion() + " @ t = " + clock);
                input.setRadiacion(clock);
                jobsSolved.add(input);
            }
        }
        //logger.info("###Deltext: "+showState());
    }

    @Override
    public void lambda() {
        if (phaseIs("done")) {
            Input input = new Input(0.0);
            oOut.addValue(input);
        }
    }
}
