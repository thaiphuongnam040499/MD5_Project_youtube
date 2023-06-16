package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.CategoryDTO;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.Category;
import rikkei.academy.service.catalogService.ICatalogService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICatalogService catalogService;

    @GetMapping("/index")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<List<Category>> index() {
        return ResponseEntity.ok(catalogService.findAll());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Category category) {
        if(category != null && category.getCategoryName() != null && category.getDescription() != null && category.getVideos() != null){
            Category savedCategory = catalogService.save(category);
            return ResponseEntity.ok(savedCategory);
        } else {
            ResponseMessage responseMessage = ResponseMessage.builder()
                    .status("Thất bại")
                    .message("Thêm mới thất bại")
                    .data("")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM')")
    public ResponseEntity<?> update(@RequestBody CategoryDTO category) {
        Category categoryUp = catalogService.findById(category.getCategoryId());
        if (category.getCategoryName() != null) {
            categoryUp.setCategoryName(category.getCategoryName());
        }else if (category.getDescription() != null) {
            categoryUp.setDescription(category.getDescription());
        }
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Sửa thể loại thành công")
                        .data(catalogService.save(categoryUp))
                        .build()
        );
    }
    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM')")
    public void delete(@PathVariable Long categoryId) {
        catalogService.delete(categoryId);
    }
}
