package com.portifolio.recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/savoryrecipes")
public class SavoryRecipesController {
            private final Map<Integer, Map<String, String>> recipes = new HashMap<>(Map.of(
                1, Map.of("name", "Strogonoff", "nationality", "Brasileira", "description", "Frango em cubos com molho cremoso de tomate e champignon"),
                2, Map.of("name", "Macarronada", "nationality", "Italiana", "description", "Massa fresca ao molho de tomate"),
                3, Map.of("name", "Escondidinho", "nationality", "Brasileira", "description", "Carne seca com purê de mandioca")
            ));

    @GetMapping
    public ResponseEntity<Map<Integer, Map<String, String>>> getAllRecipes(){
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getRecipeById(@PathVariable Integer id){
        Map<String, String> recipe = recipes.get(id);

        if (recipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Receita não encontrada", "id", String.valueOf(id)));
        }

        return ResponseEntity.ok(
            Map.of(
                "id", String.valueOf(id),
                "name", recipe.get("name"),
                "nationality", recipe.get("nationality"),
                "description", recipe.get("description")
            )
        );
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createRecipe(@RequestBody Map<String, String> newRecipe){
        Integer newId = recipes.size() + 1;
        recipes.put(newId, newRecipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Receita criada com sucesso", "id", String.valueOf(newId)));
    }
}
