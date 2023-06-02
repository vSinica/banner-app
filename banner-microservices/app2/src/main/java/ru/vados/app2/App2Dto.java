package ru.vados.app2;

import lombok.Data;
import lombok.Value;

@Data
public class App2Dto {

    @Value
    public static class App2CreateUpdateDelete {
        String info;
    }

}
