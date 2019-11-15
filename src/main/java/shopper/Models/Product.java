package shopper.Models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Product {

    @Id
    private Long id;
    private String name;
    private String description;
    private String type;
}
