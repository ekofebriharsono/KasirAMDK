package com.maseko.kasiramdk.Respon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maseko.kasiramdk.Result.ListBarangResult;

import java.util.List;

public class ResponListBarang {
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("result")
    @Expose
    private List<ListBarangResult> result = null;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<ListBarangResult> getResult() {
        return result;
    }

    public void setResult(List<ListBarangResult> result) {
        this.result = result;
    }
}
