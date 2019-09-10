package dev.esuarez.service;

import dev.esuarez.error.movementcategory.MovementCategoryNotFoundException;
import dev.esuarez.model.MovementCategory;
import dev.esuarez.repository.MovementCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementCategoryService {

    @Autowired
    private MovementCategoryRepository movementCategoryRepository;

    public List<MovementCategory> getAllMovementCategories(){
        return movementCategoryRepository.findAll();
    }

    public MovementCategory findMovementCategoriesById(Long id){
        return movementCategoryRepository.findById(id)
                .orElseThrow(() -> new MovementCategoryNotFoundException(id));
    }

    public MovementCategory createMovementCategory (MovementCategory movementCategory){
        return movementCategoryRepository.save(movementCategory);
    }

    public MovementCategory saveOrUpdate(MovementCategory movementCategory, Long id){
        return movementCategoryRepository.findById(id)
                .map( category -> {
                    category.setCategory(movementCategory.getCategory());
                    category.setImage(movementCategory.getImage());
                    category.setName(movementCategory.getName());
                    category.setUser(movementCategory.getUser());

                    return movementCategoryRepository.save(movementCategory);
                }).orElseGet(() ->{
                    movementCategory.setId(id);
                    return movementCategoryRepository.save(movementCategory);
                });
    }

    // ToDo Patch

    public ResponseEntity<?> deleteMovementCategory(Long id){
        return movementCategoryRepository.findById(id)
                .map(movementCategory -> {
                    movementCategoryRepository.delete(movementCategory);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new MovementCategoryNotFoundException(id));
    }


}