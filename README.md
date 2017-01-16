# wcomponents-samples
Sample web applications to demonstrate WComponents and provide "starter" projects

## Live Demo
A live demo of the client sample application can be viewed [here](http://wcomponents-bordertech.rhcloud.com/client/app) or the charting sample can be viewed [here](http://wcomponents-bordertech.rhcloud.com/chart/app).

## Build and Run Sample
If you wish to build the samples you will need [Apache Maven](https://maven.apache.org/) installed.

Follow these commands to fetch the sample source, build and run:

1. `git clone https://github.com/bordertech/wcomponents-samples.git` (first time only)
2. `cd wcomponents-samples`
3. `mvn install`
4. `cd client-app-sample/client-war` or `cd chart-c3-sample/chart-c3-war`
5. `mvn jetty:run`
6.  Access sample at [http://localhost:8080/app](http://localhost:8080/app)
