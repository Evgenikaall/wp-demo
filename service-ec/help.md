

```shell
curl -X POST -H "Content-Type: application/vnd.kafka.avro.v2+json" \
     --data '{
         "value_schema_id": 1,
         "records": [
             {
                 "value": {
                     "identifier": 12345,
                     "name": "John Doe"
                 }
             }
         ]
     }' \
     http://localhost:19092/topics/row-data
```


```shell

curl -X POST "http://localhost:8080/produce-message" \
     -d "identifier=12345" \
     -d "name=John Doe" \
     -H "Content-Type: application/x-www-form-urlencoded"

```