/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.cfu.com.entities;


import java.util.Collection;

public class CFBodyType {
    private static final long serialVersionUID = 1L;
    
    
    
    private Integer id;
    
    
    private String bodyTypeString;
    
    private Collection<CFAdvertisement> CFAdvertisementCollection;

    public CFBodyType() {
    }

    public CFBodyType(Integer id) {
        this.id = id;
    }

    public CFBodyType(Integer id, String bodyTypeString) {
        this.id = id;
        this.bodyTypeString = bodyTypeString;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBodyTypeString() {
        return bodyTypeString;
    }

    public void setBodyTypeString(String bodyTypeString) {
        this.bodyTypeString = bodyTypeString;
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
        if (!(object instanceof CFBodyType)) {
            return false;
        }
        CFBodyType other = (CFBodyType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    public String toString() {
        return "entities.BodyType[ id=" + id + " ]";
    }
    
}
