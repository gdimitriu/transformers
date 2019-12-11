/*
 Copyright (c) 2017 Gabriel Dimitriu All rights reserved.
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 This file is part of transformationengine project.

 transformationengine is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 transformationengine is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with transformationengine.  If not, see <http://www.gnu.org/licenses/>.
*/
package transformationsEngine;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import transformationsEngine.digester.steps.test.StaxonConfigurationTest;
import transformationsEngine.flow.configuration.test.FlowConfigurationTest;
import transformationsEngine.restCalls.test.RestCallsForDigesterTransformationsTest;
import transformationsEngine.restCalls.test.RestCallsForDummyTransformationsTest;
import transformationsEngine.restCalls.test.RestCallsForFlowTransformationsTest;
import transformationsEngine.restCalls.test.RestCallsForStaxonTransformationsTest;

@RunWith(Suite.class)
@SuiteClasses({StaxonConfigurationTest.class,
	FlowConfigurationTest.class,
	RestCallsForDigesterTransformationsTest.class, RestCallsForDummyTransformationsTest.class,
	RestCallsForFlowTransformationsTest.class, RestCallsForStaxonTransformationsTest.class})
public class AllTranformationEngineTests {

}
