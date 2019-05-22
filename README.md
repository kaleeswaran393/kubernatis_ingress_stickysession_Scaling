# kubernatis_ingress_stickysession_Scaling



Steps 

1. Backend Server - Java
     1. Endpoints are  - hit_backend, backend1, backend2, backend3 
     
2. K8s Objects     
    1. K8s Services are  - Route-Service, Backend-1, backend-2, backend-3
    2. ReplicaSet for corresponging services
    3. Horizantal Auto Scaling for ReplicaSet
    
3. Build Java Project Using Maven
    
4. Dockerfile 
    
    FROM openjdk:8-jdk-alpine
    COPY  kubia/target/kubia-1.0.0.jar kubia-1.0.0.jar
    EXPOSE 8080
    ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/kubia-1.0.0.jar"]
    
     1.docker build -t kubia:1.0.0 .
     2.docker tag kubia:1.0.0  kaleeswarankaruppusamy/e2esystem:kubia4
     3.docker push  <DOCKER_REPO>:kubia4
 
 
 5. K8S Deployment Files
      1. kubectl apply -f route-service.yaml
      2. kubectl apply -f backend-ingress.yaml
      3. kubectl apply -f backend-1.yaml
      4. kubectl apply -f backend-2.yaml
      5. kubectl apply -f backend-3.yaml
      
 
