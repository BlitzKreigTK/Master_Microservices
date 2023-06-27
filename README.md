# Microservices_Overview_with_Docker_Kubernetes

This repository contains below mentioned related notes and Practices.
1. Microservices (Basics)
2. Docker (Basics)
3. Cloud Native Applications (Basics, 12 Factor application)
4. Environment configuration using Spring Cloud Configuration
5. Service discovery using Eureka Server
6. Resilience4j (Circuit Breaker, Retry, Rate Limiter and Bulk Head Pattern)
7. Spring Cloud Gateway
8. Distributed Logging using Spring Cloud Sleuth/Micro meter and Zipkin
9. Setting up alerting systems using Micro meter, Prometheus and Grafana.

# Udemy_Learning
https://www.udemy.com/course/master-microservices-with-spring-docker-kubernetes/
https://github.com/learning/microservices-with-spring-sectionwise-code/


#Important Links
Spring Cloud Project - https://spring.io/projects/spring-cloud
Spring Cloud Config - https://spring.io/projects/spring-cloud-config
Spring Cloud Gateway - https://spring.io/projects/spring-cloud-gateway
Spring Cloud Netflix - https://spring.io/projects/spring-cloud-netflix
Spring Cloud Sleuth - https://spring.io/projects/spring-cloud-sleuth
The 12-factor App - https://12factor.net/
Docker - https://www.docker.com/
DockerHub - https://hub.docker.com/u/learning
Cloud Native Buildpacks - https://buildpacks.io/
Resilience4j - https://resilience4j.readme.io/docs/getting-started
Zipkin - https://zipkin.io/
RabbitMQ - https://www.rabbitmq.com/
Micrometer - https://micrometer.io/
Prometheus - https://prometheus.io/
Grafana - https://grafana.com/
Kubernetes - https://kubernetes.io/
GCP - https://console.cloud.google.com/
GConsole - https://cloud.google.com/sdk
Helm - https://helm.sh/
Keycloak - https://www.keycloak.org/
Istio - https://istio.io/

#Maven Commands
Maven Command													Description
"mvn clean install -Dmaven.test.skip=true"						To generate a jar inside target folder
"mvn spring-boot:run"											To start a springboot maven project
"mvn spring-boot:build-image -Dmaven.test.skip=true"			To generate a docker image using Buildpacks. No need of Dockerfile

#Docker Commands
Docker Command													Description
"docker build . -t learning/accounts"							To generate a docker image based on a Dockerfile
"docker run -p 8081:8080 learning/accounts"						To start a docker container based on a given image
"docker images"													To list all the docker images present in the Docker server
"docker image inspect image-id"									To display detailed image information for a given image id
"docker image rm image-id"										To remove one or more images for a given image ids
"docker image push docker.io/learning/accounts"					To push an image or a repository to a registry
"docker image pull docker.io/learning/accounts"					To pull an image or a repository from a registry
"docker ps"														To show all running containers
"docker ps -a"													To show all containers including running and stopped
"docker container start container-id"							To start one or more stopped containers
"docker container pause container-id"							To pause all processes within one or more containers
"docker container unpause container-id"							To unpause all processes within one or more containers
"docker container stop container-id"							To stop one or more running containers
"docker container kill container-id"							To kill one or more running containers instantly
"docker container restart container-id"							To restart one or more containers
"docker container inspect container-id"							To inspect all the details for a given container id
"docker container logs container-id"							To fetch the logs of a given container id
"docker container logs -f container-id"							To follow log output of a given container id
"docker container rm container-id"								To remove one or more containers based on container ids
"docker container prune"										To remove all stopped containers
"docker compose up"												To create and start containers based on given docker compose file
"docker compose stop"											To stop services

#Kubectl Commands
Kubernetes Command																		Description
"kubectl apply -f filename"																To create a deployment/service/configmap based on a given YAML file
"kubectl get all"																		To get all the components inside your cluster
"kubectl get pods"																		To get all the pods details inside your cluster
"kubectl get pod pod-id"																To get the details of a given pod id
"kubectl describe pod pod-id"															To get more details of a given pod id
"kubectl delete pod pod-id"																To delete a given pod from cluster
"kubectl get services"																	To get all the services details inside your cluster
"kubectl get service service-id"														To get the details of a given service id
"kubectl describe service service-id"													To get more details of a given service id
"kubectl get nodes"																		To get all the node details inside your cluster
"kubectl get node node-id"																To get the details of a given node
"kubectl get replicasets"																To get all the replica sets details inside your cluster
"kubectl get replicaset replicaset-id"													To get the details of a given replicaset
"kubectl get deployments"																To get all the deployments details inside your cluster
"kubectl get deployment deployment-id"													To get the details of a given deployment
"kubectl get configmaps"																To get all the configmap details inside your cluster
"kubectl get configmap configmap-id"													To get the details of a given configmap
"kubectl get events --sort-by=.metadata.creationTimestamp"								To get all the events occured inside your cluster
"kubectl scale deployment accounts-deployment --replicas=3"								To increase the number of replicas for a deployment inside your cluster
"kubectl rollout history deployment accounts-deployment"								To know the rollout history for a deployment inside your cluster
"kubectl logs node-id"																	To get a logs of a given node inside your cluster
"kubectl rollout undo deployment accounts-deployment --to-revision=1"					To rollback to a given revision for a deployment inside your cluster
"kubectl set image deployment accounts-deployment accounts=learning/accounts:k8s"		To set a new image for a deployment inside your cluster
"kubectl autoscale deployment accounts-deployment --min=3 --max=10 --cpu-percent=70"	To create automatic scaling using HPA for a deployment inside your cluster