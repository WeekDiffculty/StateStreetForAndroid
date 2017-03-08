package com.kevin.tech.bottomnavigationbarforandroid.fragment.subfragment.Hq.Model;

/**
 * Created by bf on 2017/2/28.
 */

public class Hqmodel {
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrince() {
        return prince;
    }

    public void setPrince(String prince) {
        this.prince = prince;
    }

    @Override
    public String toString() {
        return "Hqmodel{" +
                "symbol='" + symbol + '\'' +
                ", time='" + time + '\'' +
                ", prince='" + prince + '\'' +
                '}';
    }

    private String symbol;
    private String time;
    private String prince;
}
