/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.cfu.com.entities;
    
public class CFFavourite {
    
    private Long id;
    
    
    private CFAdvertisement CFAdvertisementId;
    
    
    private CFUser userId;

    public CFFavourite() {
    }

    public CFFavourite(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CFAdvertisement getAdvertisementId() {
        return CFAdvertisementId;
    }

    public void setAdvertisementId(CFAdvertisement CFAdvertisementId) {
        this.CFAdvertisementId = CFAdvertisementId;
    }

    public CFUser getUserId() {
        return userId;
    }

    public void setUserId(CFUser userId) {
        this.userId = userId;
    }

    
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CFFavourite)) {
            return false;
        }
        CFFavourite other = (CFFavourite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    public String toString() {
        return "Favourite[ id=" + id + " ]";
    }
    
}
