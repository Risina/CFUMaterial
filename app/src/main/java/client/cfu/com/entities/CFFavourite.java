/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.cfu.com.entities;

import com.google.android.gms.internal.id;

public class CFFavourite {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;

    public long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(long advertisementId) {
        this.advertisementId = advertisementId;
    }

    private long advertisementId;
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public CFFavourite(long userId, long CFAdvertisementId, long id) {
        this.userId = userId;
        this.advertisementId = CFAdvertisementId;
    }

    public CFFavourite(){}
}
