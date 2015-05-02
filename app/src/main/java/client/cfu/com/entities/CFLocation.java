/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.cfu.com.entities;

import java.util.Collection;

public class CFLocation {
    
    private Integer id;
    
    
    private String locationString;
    
    private Collection<CFUser> userCollection;

    public CFLocation() {
    }

    public CFLocation(Integer id) {
        this.id = id;
    }

    public CFLocation(Integer id, String locationString) {
        this.id = id;
        this.locationString = locationString;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }

    
    public Collection<CFUser> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<CFUser> userCollection) {
        this.userCollection = userCollection;
    }

    
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CFLocation)) {
            return false;
        }
        CFLocation other = (CFLocation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    public String toString() {
        return "Location[ id=" + id + " ]";
    }
    
}
