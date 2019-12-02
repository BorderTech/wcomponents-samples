# wcomponents-samples
Sample web applications to demonstrate WComponents and provide "starter" projects.

## Live Demo
A live demo of the sample applications are available at the following links:
* [Client app](https://wcomponents-samples-client.herokuapp.com/app)
* [Charting](https://wcomponents-samples-chart.herokuapp.com/app)
* [File upload](https://wcomponents-samples-fileupload.herokuapp.com/app)

## Build and Run Sample
If you wish to build the samples you will need [Apache Maven](https://maven.apache.org/) installed. Minimum requirements are `Maven 3.3.9` and `Java 8`.

Follow these commands to fetch the sample source, build and run:

1. `git clone https://github.com/bordertech/wcomponents-samples.git` (first time only)
2. `cd wcomponents-samples`
3. `mvn install`
4. Change to the sample's `lde module` directory you wish to run (eg `cd client-app-sample/client-lde`)
5. `mvn lde-exec:run`
6.  Access sample at [http://localhost:8080/app](http://localhost:8080/app)

## Client sample
A sample REST API is also provided with the Client sample at [http://localhost:8080/launchswagger](http://localhost:8080/launchswagger).
