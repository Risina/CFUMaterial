/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.cfu.com.entities;

import java.util.Collection;

public class CFFuelType {

    private Integer id;
    
    
    private String fuelTypeString;
    
    private Collection<CFAdvertisement> CFAdvertisementCollection;

    public CFFuelType() {
    }

    public CFFuelType(Integer id) {
        this.id = id;
    }

    public CFFuelType(Integer id, String fuelTypeString) {
        this.id = id;
        this.fuelTypeString = fuelTypeString;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFuelTypeString() {
        return fuelTypeString;
    }

    public void setFuelTypeString(String fuelTypeString) {
        this.fuelTypeString = fuelTypeString;
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
        if (!(object instanceof CFFuelType)) {
            return false;
        }
        CFFuelType other = (CFFuelType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    public String toString() {
        return "entities.FuelType[ id=" + id + " ]";
    }
    
}
