package com.todo.serviceImpl;

import com.todo.entities.ToDoItem;
import com.todo.models.ToDoItemBindingModel;
import com.todo.models.ToDoItemViewModel;
import com.todo.repositories.ToDoItemRepository;
import com.todo.services.ToDoItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ToDoItemServiceImpl implements ToDoItemService {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<ToDoItemViewModel> getAllItems() {
        List<ToDoItem> categories = this.toDoItemRepository.findAll();
        List<ToDoItemViewModel> categoryViewModels = new ArrayList<>();
        for (ToDoItem toDoItem : categories) {
            ToDoItemViewModel model = this.modelMapper.map(toDoItem, ToDoItemViewModel.class);
            categoryViewModels.add(model);
        }

        return categoryViewModels;
    }

    @Override
    public List<ToDoItemViewModel> getAllItemsByCategoryId(long categoryId) {
        List<ToDoItem> categories = this.toDoItemRepository.findAllByCategoryId(categoryId);
        List<ToDoItemViewModel> categoryViewModels = new ArrayList<>();
        for (ToDoItem toDoItem : categories) {
            ToDoItemViewModel model = this.modelMapper.map(toDoItem, ToDoItemViewModel.class);
            categoryViewModels.add(model);
        }

        return categoryViewModels;
    }

    @Override
    public ToDoItemViewModel getItemById(long id) {
        ToDoItem toDoItem = this.toDoItemRepository.findOne(id);
        ToDoItemViewModel model = this.modelMapper.map(toDoItem, ToDoItemViewModel.class);
        return model;
    }

    @Override
    public void saveItem(ToDoItemBindingModel toDoItemBindingModel) {
        ToDoItem toDoItem = this.modelMapper.map(toDoItemBindingModel, ToDoItem.class);
        this.toDoItemRepository.save(toDoItem);
    }

    @Override
    @Transactional
    public void updateItem(long itemId, ToDoItemBindingModel toDoItemBindingModel) {
        ToDoItem toDoItem = this.toDoItemRepository.findOne(itemId);
        toDoItem.setName(toDoItemBindingModel.getName());
        toDoItem.setDeadline(toDoItemBindingModel.getDeadline());
        toDoItem.setEnabled(toDoItemBindingModel.isEnabled());
    }

    @Override
    public void deleteById(long id) {
        this.toDoItemRepository.delete(id);
    }
}
