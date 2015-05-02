/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.cfu.com.entities;

import java.util.Collection;
import java.util.Date;

public class CFUser {

    private Long id;
    
    
    private String uName;
    
    
    private String email;
    
    
    private String phoneNo1;
    
    private String phoneNo2;
    
    
    private String phoneNo3;
    
    
    private String uType;
    
    
    private String password;
    
    
    
    private Date createdDate;
    
    
    
    private Date modifiedDate;
    
    
    private CFLocation locationId;
    
    private Collection<CFFavourite> favouriteCollection;
    
    private Collection<CFAdvertisement> CFAdvertisementCollection;

    public CFUser() {
    }

    public CFUser(Long id) {
        this.id = id;
    }

    public CFUser(Long id, String uName, String email, String phoneNo1, String phoneNo3, String uType, String password, Date createdDate, Date modifiedDate) {
        this.id = id;
        this.uName = uName;
        this.email = email;
        this.phoneNo1 = phoneNo1;
        this.phoneNo3 = phoneNo3;
        this.uType = uType;
        this.password = password;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo1() {
        return phoneNo1;
    }

    public void setPhoneNo1(String phoneNo1) {
        this.phoneNo1 = phoneNo1;
    }

    public String getPhoneNo2() {
        return phoneNo2;
    }

    public void setPhoneNo2(String phoneNo2) {
        this.phoneNo2 = phoneNo2;
    }

    public String getPhoneNo3() {
        return phoneNo3;
    }

    public void setPhoneNo3(String phoneNo3) {
        this.phoneNo3 = phoneNo3;
    }

    public String getUType() {
        return uType;
    }

    public void setUType(String uType) {
        this.uType = uType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public CFLocation getLocationId() {
        return locationId;
    }

    public void setLocationId(CFLocation locationId) {
        this.locationId = locationId;
    }

    
    public Collection<CFFavourite> getFavouriteCollection() {
        return favouriteCollection;
    }

    public void setFavouriteCollection(Collection<CFFavourite> favouriteCollection) {
        this.favouriteCollection = favouriteCollection;
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
        if (!(object instanceof CFUser)) {
            return false;
        }
        CFUser other = (CFUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    public String toString() {
        return "entities.User[ id=" + id + " ]";
    }
    
}
