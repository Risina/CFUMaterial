/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.cfu.com.entities;

import java.util.Collection;

import java.util.Collection;

public class CFConditionType {

    private Integer id;
    
    
    private String conditionString;
    
    private Collection<CFAdvertisement> advertisementCollection;

    public CFConditionType() {
    }

    public CFConditionType(Integer id) {
        this.id = id;
    }

    public CFConditionType(Integer id, String conditionString) {
        this.id = id;
        this.conditionString = conditionString;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConditionString() {
        return conditionString;
    }

    public void setConditionString(String conditionString) {
        this.conditionString = conditionString;
    }

    
    public Collection<CFAdvertisement> getAdvertisementCollection() {
        return advertisementCollection;
    }

    public void setAdvertisementCollection(Collection<CFAdvertisement> advertisementCollection) {
        this.advertisementCollection = advertisementCollection;
    }

    
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CFConditionType)) {
            return false;
        }
        CFConditionType other = (CFConditionType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    public String toString() {
        return "entities.ConditionType[ id=" + id + " ]";
    }
    
}
