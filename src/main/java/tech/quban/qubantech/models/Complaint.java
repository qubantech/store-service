package tech.quban.qubantech.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
public class Complaint implements Serializable {
    @Id
    private String id;
    private String status;
    private Long dateSent;
    private Long dateResponded;
    private String userId;
    private String theme;
    private String text;
    private String[] tags;
    private String[] keywords;
    private Long institutionId;
    private String response;
    private Double score;
}
