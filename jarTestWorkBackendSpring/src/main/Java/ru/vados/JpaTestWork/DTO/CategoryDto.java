package ru.vados.JpaTestWork.DTO;

public class CategoryDto {

    private String category_name;
    private String categoryReqId;
    private Long idCategory;

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategoryReqId() {
        return categoryReqId;
    }

    public void setCategoryReqId(String categoryReqId) {
        this.categoryReqId = categoryReqId;
    }
}
