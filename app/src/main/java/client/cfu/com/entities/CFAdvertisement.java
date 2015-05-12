package client.cfu.com.entities;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class CFAdvertisement implements Serializable{
    private Long id;


    private String title;


    private String description;


    private long price;


    private boolean isPriceNegotiable;


    private String imageLocation;


    private String model;


    private short modelYear;


    private int engineCapacity;


    private long milage;


    private Date createdDate;


    private Date modifiedDate;

    private Collection<CFFavourite> favouriteCollection;


    private CFFuelType fuelTypeId;


    private CFTransmissionType transmissionTypeId;


    private CFConditionType conditionId;


    private CFBodyType bodyTypeId;


    private CFBrand brandId;


    private CFVehicleType vehicleTypeId;


    private CFUser userId;

    public CFAdvertisement() {
    }

    public CFAdvertisement(Long id) {
        this.id = id;
    }

    public CFAdvertisement(Long id, String title, String description, long price, boolean isPriceNegotiable, String imageLocation, String model, short modelYear, int engineCapacity, long milage, Date createdDate, Date modifiedDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.isPriceNegotiable = isPriceNegotiable;
        this.imageLocation = imageLocation;
        this.model = model;
        this.modelYear = modelYear;
        this.engineCapacity = engineCapacity;
        this.milage = milage;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean getIsPriceNegotiable() {
        return isPriceNegotiable;
    }

    public void setIsPriceNegotiable(boolean isPriceNegotiable) {
        this.isPriceNegotiable = isPriceNegotiable;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public short getModelYear() {
        return modelYear;
    }

    public void setModelYear(short modelYear) {
        this.modelYear = modelYear;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public long getMilage() {
        return milage;
    }

    public void setMilage(long milage) {
        this.milage = milage;
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


    public Collection<CFFavourite> getFavouriteCollection() {
        return favouriteCollection;
    }

    public void setFavouriteCollection(Collection<CFFavourite> favouriteCollection) {
        this.favouriteCollection = favouriteCollection;
    }

    public CFFuelType getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(CFFuelType fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public CFTransmissionType getTransmissionTypeId() {
        return transmissionTypeId;
    }

    public void setTransmissionTypeId(CFTransmissionType transmissionTypeId) {
        this.transmissionTypeId = transmissionTypeId;
    }

    public CFConditionType getConditionId() {
        return conditionId;
    }

    public void setConditionId(CFConditionType conditionId) {
        this.conditionId = conditionId;
    }

    public CFBodyType getBodyTypeId() {
        return bodyTypeId;
    }

    public void setBodyTypeId(CFBodyType bodyTypeId) {
        this.bodyTypeId = bodyTypeId;
    }

    public CFBrand getBrandId() {
        return brandId;
    }

    public void setBrandId(CFBrand brandId) {
        this.brandId = brandId;
    }

    public CFVehicleType getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(CFVehicleType vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public CFUser getUserId() {
        return userId;
    }

    public void setUserId(CFUser userId) {
        this.userId = userId;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
