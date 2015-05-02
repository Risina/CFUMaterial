/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.cfu.com.entities;

import java.util.Collection;

public class CFBrand {

    private Integer id;
    
    
    private String brandString;
    
    private Collection<CFAdvertisement> CFAdvertisementCollection;

    public CFBrand() {
    }

    public CFBrand(Integer id) {
        this.id = id;
    }

    public CFBrand(Integer id, String brandString) {
        this.id = id;
        this.brandString = brandString;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandString() {
        return brandString;
    }

    public void setBrandString(String brandString) {
        this.brandString = brandString;
    }

    
    public Collection<CFAdvertisement> getAdvertisementCollection() {
        return CFAdvertisementCollection;
    }

    public void setAdvertisementCollection(Collection<CFAdvertisement> CFAdvertisementCollection) {
        this.CFAdvertisementCollection = CFAdvertisementCollection;
    }

    
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CFBrand)) {
            return false;
        }
        CFBrand other = (CFBrand) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    public String toString() {
        return "entities.Brand[ id=" + id + " ]";
    }
    
}
