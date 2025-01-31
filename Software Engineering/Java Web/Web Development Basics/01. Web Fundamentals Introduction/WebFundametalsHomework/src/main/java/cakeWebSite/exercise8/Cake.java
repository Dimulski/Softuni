package cakeWebSite.exercise8;

import java.io.Serializable;
import java.math.BigDecimal;

public class Cake implements Serializable {

    private String name;

    private BigDecimal price;

    public Cake(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
