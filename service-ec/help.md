
## run this script before running application
```shell
curl -X POST http://localhost:8081/subjects/com.wp.model.Person/versions \
-H "Content-Type: application/vnd.schemaregistry.v1+json" \
-d '{
  "schema": "{\"type\":\"record\",\"name\":\"Person\",\"namespace\":\"com.wp.model\",\"fields\":[{\"name\":\"identifier\",\"type\":\"long\"},{\"name\":\"name\",\"type\":\"string\"}]}"
}'

curl -X GET http://localhost:8081/subjects
```

## run this script to go through the flow
```shell
curl -X POST "http://localhost:8080/produce-message" \
     -d "identifier=12345" \
     -d "name=John Doe" \
     -H "Content-Type: application/x-www-form-urlencoded"
```