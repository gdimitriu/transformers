TransformationEngine project.
=

The project transformationsEngine is an example on how to use the digester and some transformations engines.
 
 Transformation engines used by this project are:
 -  staxon for json2xml and xml2json transformation
 -  saxon for xslt mapping.
 -  custom transformation
 
 Apache digester is used for easy flow creation.
 The jersey with embedded jetty is used to inject data into engine.
 
 transformationsEngine.flow.configuration.test and transformationsEngine.digester.steps.test contains Unitests for JAXB bindings of the configurations.
 
 transformationsEngine.restCalls.test contains functional tests (junit implementations) for testing steps:
 - RestCallsForDigesterTransformationsTest contains transformations using digester.
 - RestCallsForFlowTransformationsTest contains the same transformations from digester but using flow resource.
 - RestCallsForStaxonTransfomrationsTest contains json2xml and xml2json transformations.
 - ProcessingRestTestManual is the client used to run manual tests. The server is transformationsEngine.serverRest.ServerJetty.java.
 
The docker files to create services using docker, docker-compose and docker swarm are in repository :

https://github.com/gdimitriu/dockers/tree/master/builders/transformers/transformationsEngine

and the already created image is in repository:

https://hub.docker.com/repository/docker/gdimitriu/transformations-engine
