echo "Пример передачи самого простого JSON'а:"
echo '{"requestMethod":"GET","url":"http://localhost:8080/json"}'

# docker run -it --rm --network docker_compose_kafka-network confluentinc/cp-kafka:7.4.4 /bin/kafka-console-producer --bootstrap-server kafka:29092 --topic test_topic
docker run -it --rm --network docker_compose_default confluentinc/cp-kafka:7.4.4 /bin/kafka-console-producer --bootstrap-server kafka:9092 --topic test_topic