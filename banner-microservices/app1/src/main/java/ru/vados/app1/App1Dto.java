package ru.vados.app1;

import lombok.Data;
import lombok.Value;

@Data
public class App1Dto {

    @Value
    public static class App1CreateUpdateDelete {
        String info;
    }

}
