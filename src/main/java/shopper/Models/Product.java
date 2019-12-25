package shopper.Models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "product")
@RequiredArgsConstructor
public class Product implements Serializable {

    @Id
    @NotNull(message = "Price must be at least 1 letter")
    private Integer id;

    @Size(min = 1, message = "Name must be at least 1 letter")
    @Size(max = 250, message = "Name must be lower than 250 letters")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Price must be at least 1 letter")
    @Column(name = "price")
    private Float price;

    @Size(min = 10, message = "Description must be at least 10 letters")
    @Column(name = "description")
    private String description;

    @NotBlank(message = "Please, provide a type")
    @Column(name = "type")
    private String type;

    @Size(max = 50)
    @Column(name = "username")
    private String username;
}
