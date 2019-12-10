package shopper.Models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "product_type")
@RequiredArgsConstructor
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Size(min = 1, message = "Type must be at least 1 letter")
    @Size(max = 40, message = "Name must be lower than 40 letters")
    @Column(name = "type")
    private String type;
}
