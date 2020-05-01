package com.maseko.kasiramdk.Respon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maseko.kasiramdk.Result.ListTransaksi;

import java.util.List;

public class ResponListTransaksi {

    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("result")
    @Expose
    private List<ListTransaksi> result = null;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<ListTransaksi> getResult() {
        return result;
    }

    public void setResult(List<ListTransaksi> result) {
        this.result = result;
    }
}
