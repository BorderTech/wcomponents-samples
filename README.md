# wcomponents-samples
Sample web applications to demonstrate WComponents and provide "starter" projects

## Live Demo
A live demo of the sample applications are available at the following links:
* [Client app](https://wcomponents-bordertech.rhcloud.com/client/app)
* [Charting](https://wcomponents-bordertech.rhcloud.com/chart/app)
* [Fileupload](https://wcomponents-bordertech.rhcloud.com/fileupload/app)

## Build and Run Sample
If you wish to build the samples you will need [Apache Maven](https://maven.apache.org/) installed.

Follow these commands to fetch the sample source, build and run:

1. `git clone https://github.com/bordertech/wcomponents-samples.git` (first time only)
2. `cd wcomponents-samples`
3. `mvn install`
4. Change to the sample's `war module` directory you wish to run (eg `cd client-app-sample/client-war`)
5. `mvn jetty:run`
6.  Access sample at [http://localhost:8080/app](http://localhost:8080/app)
