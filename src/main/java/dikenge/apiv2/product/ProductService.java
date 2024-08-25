package dikenge.apiv2.product;

import dikenge.apiv2.product.Exceptions.ProductNotFoundException;
import dikenge.apiv2.product.model.ProductDto;
import dikenge.apiv2.product.model.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final ModelMapper mapper;

    public ResponseEntity<ProductDto> create(ProductEntity request)
    {
       var saved = mapper.map(request, ProductEntity.class);
       productRepo.saveAndFlush(saved);
       var resp = mapper.map(saved, ProductDto.class);
       return ResponseEntity.status(HttpStatus.CREATED).body(resp);

    }

    public ResponseEntity<List<ProductDto>> getAll() {
        List<ProductEntity> productEntities = productRepo.findAll();
        List<ProductDto> resp = new ArrayList<>();
        productEntities.forEach(
                product -> resp.add(mapper.map(product, ProductDto.class))
        );
        return ResponseEntity.ok(resp);

    }

    public ResponseEntity<Void> delete(UUID id)
    {
        Optional<ProductEntity> product = productRepo.findById(id);
        if(product.isPresent())
        {
            productRepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new ProductNotFoundException();

    }


    public ResponseEntity<ProductDto> update(UUID id, ProductEntity request)
    {
        Optional<ProductEntity> product = productRepo.findById(id);
        if(product.isPresent())
        {
            ProductEntity productEntity = product.get();
            productEntity.setName(request.getName());
            productEntity.setPrice(request.getPrice());
            productEntity.setDescription(request.getDescription());
            productRepo.save(productEntity);
            return ResponseEntity.ok(
                    mapper.map(productEntity, ProductDto.class)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProductDto());

    }

    public ResponseEntity<ProductDto> getById(UUID id) {
        Optional<ProductEntity> product = productRepo.findById(id);
        return product.map(
                productEntity -> ResponseEntity.ok(mapper.map(productEntity, ProductDto.class)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
