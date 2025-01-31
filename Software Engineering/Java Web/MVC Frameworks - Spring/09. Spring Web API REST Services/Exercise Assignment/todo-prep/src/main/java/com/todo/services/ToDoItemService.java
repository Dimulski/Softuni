package com.todo.services;

import com.todo.models.ToDoItemBindingModel;
import com.todo.models.ToDoItemViewModel;

import java.util.List;

public interface ToDoItemService {

    List<ToDoItemViewModel> getAllItems();

    List<ToDoItemViewModel> getAllItemsByCategoryId(long categoryId);

    ToDoItemViewModel getItemById(long id);

    void saveItem(ToDoItemBindingModel toDoItemBindingModel);

    void updateItem(long itemId, ToDoItemBindingModel toDoItemBindingModel);

    void deleteById(long id);
}
