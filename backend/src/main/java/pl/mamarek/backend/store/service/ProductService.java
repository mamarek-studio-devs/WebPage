package pl.mamarek.backend.store.service;

import lombok.RequiredArgsConstructor;
import pl.mamarek.backend.store.model.Product;
import pl.mamarek.backend.store.model.ProductDto;
import pl.mamarek.backend.store.model.ProductMapper;
import pl.mamarek.backend.store.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<ProductDto> getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toDto);
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Transactional
    public Optional<ProductDto> updateProduct(Long id, ProductDto productDto) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    productMapper.updateEntityFromDto(productDto, existingProduct);
                    Product savedProduct = productRepository.save(existingProduct);
                    return productMapper.toDto(savedProduct);
                });
    }

    @Transactional
    public Optional<ProductDto> partialUpdateProduct(Long id, ProductDto productDto) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    productMapper.partialUpdateEntityFromDto(productDto, existingProduct);
                    Product savedProduct = productRepository.save(existingProduct);
                    return productMapper.toDto(savedProduct);
                });
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }
}
