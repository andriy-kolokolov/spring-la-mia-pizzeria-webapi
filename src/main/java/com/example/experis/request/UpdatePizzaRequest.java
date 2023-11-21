package com.example.experis.request;

import com.example.experis.model.Pizza;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePizzaRequest {

    private Pizza pizza;
    private List<Long> ingredientIds;

}
