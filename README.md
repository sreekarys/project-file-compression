# project-file-compression

This is a maven project. Follow the below steps -
	To build the project - mvn clean install 
	To execute the project - mvn exec:java -Dexec.mainClass="com.project.Main" or alternatively, you can run Main.java as a Java Application from an IDE.

The application properties are present in src/main/resources/app.properties. Application restart is required when any property is changed.

Proper validations are done on the inputs provided to the application. The input and output field values should be directories.
All the classes are unit tested.
Integration tests are present in OrchestratorTest.java. These tests try to compress and decompress different use cases - nested directories, empty directories etc...
Most of the corner cases are covered during the integration testing.

