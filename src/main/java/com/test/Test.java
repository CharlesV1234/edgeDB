package com.test;
import io.quarkus.runtime.QuarkusApplication;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import com.edgedb.driver.EdgeDBClient;
import com.edgedb.driver.exceptions.EdgeDBException;
import com.edgedb.driver.EdgeDBClient;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test implements QuarkusApplication {

    @Override
    public int run(String... args) throws Exception {

        createSubType().thenAccept(result -> {
            System.out.println("Creating SubType " + result);
            JSONArray jsonArray = new JSONArray(result);
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String idString = jsonObject.getString("id");
                UUID id = UUID.fromString(idString);
                System.out.println("Parsed id: " + id);
                try {
                    getSubType(id).thenAccept(subType -> {
                        System.out.println("Retrieved SubType " + subType);
                    });
                    getSubTypeObject(id).thenAccept(subType -> {
                        System.out.println(subType.id);
                        System.out.println(subType.name);
                        System.out.println(subType.designation);
                        ;
                    })
                    ;
                } catch (IOException | EdgeDBException e) {
                    e.printStackTrace();
                }
            }
        });

        return 0;
    }

    public CompletionStage<String> createSubType() throws IOException, EdgeDBException {
        EdgeDBClient client = new EdgeDBClient();
        StringBuilder queryBuilder = new StringBuilder("Insert SubType { ");
        queryBuilder.append("name :=  ").append("\"John\"");

        queryBuilder.append(" ,designation :=  ").append("\"Doe\"");
        queryBuilder.append("}");
        return client.queryJson(queryBuilder.toString())
                .thenApply(result -> {
                    System.out.println(result.getValue());
                    return result.getValue();
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    public CompletionStage<String> getSubType(UUID id) throws IOException, EdgeDBException {
        EdgeDBClient client = new EdgeDBClient();
        return client.queryJson("SELECT SubType { * } FILTER .id = <uuid>$id",
                new JSONObject().put("id", id).toMap())
                .thenApply(result -> {
                    System.out.println(result.getValue());
                    return result.getValue();
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    public CompletionStage<SubType> getSubTypeObject(UUID id) throws IOException, EdgeDBException {
        EdgeDBClient client = new EdgeDBClient();
        return client.querySingle(SubType.class,"SELECT SubType { * } FILTER .id = <uuid>$id",
                new JSONObject().put("id", id).toMap())
                .thenApply(result -> {
                    return result;
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

}