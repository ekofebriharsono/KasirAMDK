package com.maseko.kasiramdk.Respon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.maseko.kasiramdk.Result.ListKeranjangPelangganResult;

import java.util.List;

public class ResponKeranjang {

    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("result")
    @Expose
    private List<ListKeranjangPelangganResult> result = null;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<ListKeranjangPelangganResult> getResult() {
        return result;
    }

    public void setResult(List<ListKeranjangPelangganResult> result) {
        this.result = result;
    }
}
