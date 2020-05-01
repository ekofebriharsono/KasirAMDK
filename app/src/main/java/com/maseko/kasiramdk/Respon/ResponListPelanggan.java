package com.maseko.kasiramdk.Respon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maseko.kasiramdk.Result.ListPelangganResult;

import java.util.List;

public class ResponListPelanggan {
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("result")
    @Expose
    private List<ListPelangganResult> result = null;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<ListPelangganResult> getResult() {
        return result;
    }

    public void setResult(List<ListPelangganResult> result) {
        this.result = result;
    }
}
