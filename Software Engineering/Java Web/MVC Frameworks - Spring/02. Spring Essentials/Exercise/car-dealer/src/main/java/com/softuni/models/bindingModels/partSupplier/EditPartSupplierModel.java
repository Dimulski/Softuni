package com.softuni.models.bindingModels.partSupplier;

public class EditPartSupplierModel {

    private Long id;
    private String name;
    private Boolean isImporter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getImporter() {
        return isImporter;
    }

    public void setImporter(Boolean importer) {
        isImporter = importer;
    }
}
