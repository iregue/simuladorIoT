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

import java.nio.file.Paths;
import java.util.logging.Level;

import xdevs.core.modeling.Coupled;
import xdevs.core.simulation.Coordinator;
import xdevs.core.util.DevsLogger;

/**
 *
 * @author jlrisco
 */
public class Gpt extends Coupled {

    public Gpt(String name, double period, double observationTime) {
    	super(name);
        
		String userDirectory = Paths.get("")
		        .toAbsolutePath()
		        .toString();
		
        Generator ap1 = new Generator("ap1", period, userDirectory + "/data/ap1/");
        super.addComponent(ap1);
        Generator ap5 = new Generator("ap5", period, userDirectory + "/data/ap5/");
        super.addComponent(ap5);
        Generator ap6 = new Generator("ap6", period, userDirectory + "/data/ap6/");
        super.addComponent(ap6);
        Generator ap7 = new Generator("ap7", period, userDirectory + "/data/ap7/");
        super.addComponent(ap7);
        Generator dh1 = new Generator("dh1", period, userDirectory + "/data/dh1/");
        super.addComponent(dh1);
        Generator dh2 = new Generator("dh2", period, userDirectory + "/data/dh2/");
        super.addComponent(dh2);
        Generator dh3 = new Generator("dh3", period, userDirectory + "/data/dh3/");
        super.addComponent(dh3);
        Generator dh4 = new Generator("dh4", period, userDirectory + "/data/dh4/");
        super.addComponent(dh4);
        Generator dh5 = new Generator("dh5", period, userDirectory + "/data/dh5/");
        super.addComponent(dh5);
        Generator dh6 = new Generator("dh6", period, userDirectory + "/data/dh6/");
        super.addComponent(dh6);
        Generator dh7 = new Generator("dh7", period, userDirectory + "/data/dh7/");
        super.addComponent(dh7);
        Generator dh8 = new Generator("dh8", period, userDirectory + "/data/dh8/");
        super.addComponent(dh8);
        Generator dh9 = new Generator("dh9", period, userDirectory + "/data/dh9/");
        super.addComponent(dh9);
        Generator dh10 = new Generator("dh10", period, userDirectory + "/data/dh10/");
        super.addComponent(dh10);
        
    	SimuladorIoT simulator = new SimuladorIoT("simulador1", 3*period);
        super.addComponent(simulator);

        
        Transducer transducer = new Transducer("transducer", observationTime);
        super.addComponent(transducer);
        //super.addCoupling(generator.oOut, simulator.iIn);
        
        super.addCoupling(ap1.oOut, simulator.iIn);
        super.addCoupling(ap5.oOut, simulator.iIn);
        super.addCoupling(ap6.oOut, simulator.iIn);
        super.addCoupling(ap7.oOut, simulator.iIn);
        super.addCoupling(dh1.oOut, simulator.iIn);
        super.addCoupling(dh2.oOut, simulator.iIn);
        super.addCoupling(dh3.oOut, simulator.iIn);
        super.addCoupling(dh4.oOut, simulator.iIn);
        super.addCoupling(dh5.oOut, simulator.iIn);
        super.addCoupling(dh6.oOut, simulator.iIn);
        super.addCoupling(dh7.oOut, simulator.iIn);
        super.addCoupling(dh8.oOut, simulator.iIn);
        super.addCoupling(dh9.oOut, simulator.iIn);
        super.addCoupling(dh10.oOut, simulator.iIn);

        
        super.addCoupling(simulator.oOut, transducer.iSolved);
        super.addCoupling(transducer.oOut, ap1.iStop);
        super.addCoupling(transducer.oOut, ap5.iStop);
        super.addCoupling(transducer.oOut, ap6.iStop);
        super.addCoupling(transducer.oOut, ap7.iStop);
        super.addCoupling(transducer.oOut, dh1.iStop);
        super.addCoupling(transducer.oOut, dh2.iStop);
        super.addCoupling(transducer.oOut, dh3.iStop);
        super.addCoupling(transducer.oOut, dh4.iStop);
        super.addCoupling(transducer.oOut, dh5.iStop);
        super.addCoupling(transducer.oOut, dh6.iStop);
        super.addCoupling(transducer.oOut, dh7.iStop);
        super.addCoupling(transducer.oOut, dh8.iStop);
        super.addCoupling(transducer.oOut, dh9.iStop);
        super.addCoupling(transducer.oOut, dh10.iStop);

    }

    public static void main(String args[]) {
        DevsLogger.setup(Level.INFO);
        Gpt gpt = new Gpt("gpt", 1, 200000);
        //CoordinatorParallel coordinator = new CoordinatorParallel(gpt);
        Coordinator coordinator = new Coordinator(gpt);
        //RTCentralCoordinator coordinator = new RTCentralCoordinator(gpt);
        coordinator.initialize();
        coordinator.simulate(Long.MAX_VALUE);
    }

}
