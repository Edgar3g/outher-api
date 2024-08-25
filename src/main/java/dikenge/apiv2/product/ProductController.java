package dikenge.apiv2.product;

import dikenge.apiv2.product.model.ProductDto;
import dikenge.apiv2.product.model.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") UUID id) {
        return productService.getById(id);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAllProducts() {
        return productService.getAll();
    }
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductEntity request)
    {
        return productService.create(request);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") UUID id,
                                                    @RequestBody ProductEntity request)
    {
        return productService.update(id, request);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") UUID id)
    {
        return productService.delete(id);
    }
}
