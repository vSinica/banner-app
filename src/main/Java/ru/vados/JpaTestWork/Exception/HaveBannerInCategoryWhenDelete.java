package ru.vados.JpaTestWork.Exception;

public class HaveBannerInCategoryWhenDelete extends RuntimeException{
    public HaveBannerInCategoryWhenDelete(String message) {
        super(message);
    }
}
