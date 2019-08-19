# Kafka + ELK PoC
[Client Service](https://github.com/p0keT/kafka-client)

[Report Service](https://github.com/p0keT/kafka-elk)

This PoC consists of two services (client service and report service). 
Report service produce analytical reports based on some user data.
Namely, it finds X's prime number, for example:
Prime numbers – 2,3,5,7,11...
* User sends 5: -> return 11
* User sends 100: -> return 541
* User sends 2000: -> return 17389
Client service can:
* submit a request to produce a “report”
* check status of report production
* retrieve report once completed

Kafka is used for messages exchanging between client service and report service instances.
ELK stack log sent requests and results of calculation.



## External software

You should install:
* elasticsearch 7.3.0
* logstash 7.3.0
* kibana 7.2.1
* kafka 2.3.0

## Starting
0. Install and configure all required software.
1. Start zookeeper and kafka servers.
2. Create needed topics (report_requests, completed_reports, report_status).
3. Configure application.properties in service instances.
4. Start Elasticsearch and Kibana instances.
5. Configure logstash.conf in report service instance.
6. Start Logstash instance ```logstash -f path/to/logstash.conf```.
7. Start client and report instances.
8. Open .../report/new page
