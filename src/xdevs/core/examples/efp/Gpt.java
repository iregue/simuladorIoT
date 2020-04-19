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
import java.util.logging.Level;

import xdevs.core.modeling.Coupled;
import xdevs.core.modeling.Input;
import xdevs.core.simulation.Coordinator;
import xdevs.core.util.DevsLogger;

/**
 *
 * @author jlrisco
 */
public class Gpt extends Coupled {

    public Gpt(String name, double period, double observationTime) {
    	super(name);
        Generator generator = new Generator("generator", period);
        super.addComponent(generator);
        ArrayList<SimuladorIoT> simulator_list = new ArrayList<SimuladorIoT>();
    	SimuladorIoT simulator = new SimuladorIoT("simulador1", 3*period);
        super.addComponent(simulator);

        /*
        for(int i =0; i <100; i++) {
        	String simulador_id = "simulator_" + i;
        	SimuladorIoT simulator = new SimuladorIoT(simulador_id, 3*period);
            super.addComponent(simulator);
            simulator_list.add(simulator);
            super.addCoupling(generator.oOut, simulator.iIn);
            //super.addCoupling(simulator.oOut, transducer.iSolved);

        }
        */
        //SimuladorIoT simulator = new SimuladorIoT("simulator", 3*period);
        //super.addComponent(simulator);
        Transducer transducer = new Transducer("transducer", observationTime);
        super.addComponent(transducer);
        
        super.addCoupling(generator.oOut, simulator.iIn);
        super.addCoupling(simulator.oOut, transducer.iSolved);
        /*
        super.addCoupling(generator.oOut, transducer.iArrived);
        for(int i =0; i <100; i++) {
        	SimuladorIoT simulator = simulator_list.get(i);
        	super.addCoupling(simulator.oOut, transducer.iSolved);
        }
        */
        super.addCoupling(transducer.oOut, generator.iStop);
    }

    public static void main(String args[]) {
        DevsLogger.setup(Level.INFO);
        Gpt gpt = new Gpt("gpt", 1, 100);
        //CoordinatorParallel coordinator = new CoordinatorParallel(gpt);
        Coordinator coordinator = new Coordinator(gpt);
        //RTCentralCoordinator coordinator = new RTCentralCoordinator(gpt);
        coordinator.initialize();
        coordinator.simulate(Long.MAX_VALUE);
    }

}
