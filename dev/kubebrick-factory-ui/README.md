# Bookstore UI

# Local test

npm install

export PORT=9090
export FACTORYRESTAPIURL=http://<HOST>:<PORT>

Local test:
export PORT=9090
export FACTORYRESTAPIURL=http://localhost:9080


node app.js

# OCP setup
To test this sub project on ocp:



```
oc new-build --image-stream=openshift/nodejs:latest --name=kubebrick-ui --binary=true
oc start-build kubebrick-ui --from-dir=kubebrick-ui 
oc new-app kubebrick-ui -e PORT="8080" -e FACTORYRESTAPIURL="booksurl"
```

```
oc expose svc/kubebrick-ui
```