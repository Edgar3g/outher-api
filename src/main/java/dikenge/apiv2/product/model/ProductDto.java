package dikenge.apiv2.product.model;

import lombok.Data;

@Data
public class ProductDto {
    private String id;
    private String name;
    private String description;
    private Double price;

}
