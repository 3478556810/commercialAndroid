package com.example.carouselfigure.entity.Bmobentity;

import cn.bmob.v3.BmobObject;

public class history extends BmobObject {
    private int historyId;
    private String intro;
private String prize;

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
}
