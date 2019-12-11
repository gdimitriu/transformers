Transformers Project
=

The project contains project :

The project transformationsEngine is an example on how to use the digester and some transformations engines.
 
 Transformation engines used by this project are:
 -  staxon for json2xml and xml2json transformation
 -  saxon for xslt mapping.
 - custom transformation
 
 Apache digester is used for easy flow creation.
 The jersey with embedded jetty is used to inject data into engine.
 
 transformationsEngine.flow.configuration.test and transformationsEngine.digester.steps.test contains Unitests for JAXB bindings of the configurations.
 
 transformationsEngine.restCalls.test contains functional tests (junit implementations) for testing steps:
 - RestCallsForDigesterTransformationsTest contains transformations using digester.
 - RestCallsForFlowTransformationsTest contains the same transformations from digester but using flow resource.
 - RestCallsForStaxonTransfomrationsTest contains json2xml and xml2json transformations.
 - ProcessingRestTestManual is the client used to run manual tests. The server is transformationsEngine.serverRest.ServerJetty.java.
 
 
The outcome of this project is used in chappy project.

Those project should be compiled and run only with java 8.
=

For java 9 the unitests should be moved to another project and digester should be removed because some dependencies have been removed from java 11.

The conclusions of this project and later on chappy and skynet are:
-
- digester is good for what is was created but for transformer flow configuration is better to have jaxb.
- digester is no longer maintained so it will have problem with java 9 modules and removed libraries.
- digester is more complicated to maintain if the configuration is changed or the parameters of calls are changed.
- jaxb is better suite to maintain configuration.
- classed created by jaxb could be use in stateless configuration and they are instanciated only once per flow.
- digester should be run for each input message even the same flow is used
- the digester caching mechanism could be implemented but is more complicated than jaxb implementation.
