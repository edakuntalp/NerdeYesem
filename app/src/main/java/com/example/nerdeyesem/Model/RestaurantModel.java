package com.example.nerdeyesem.Model;

public class RestaurantModel {
    private String Id;              //ID of the restaurant
    private String Name;            //Name of the restaurant
    private String Url;             //URL of the restaurant page
    private String Latitude;        //Restaurant latitude details
    private String Longitude;        //Restaurant longitude details
    private String Address;         //Complete address of the restaurant
    private String FeaturedImage;  //URL of the high resolution header image of restaurant
    private String UserRating;     //Restaurant rating details

    public RestaurantModel(String id, String name, String url, String latitude, String longitude,
                           String featuredImage, String userRating, String address){
        Id = id;
        Name = name;
        Url = url;
        Latitude = latitude;
        Longitude = longitude;
        FeaturedImage = featuredImage;
        UserRating = userRating;
        Address = address;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getFeaturedImage() {
        return FeaturedImage;
    }

    public void setFeaturedImage(String featuredImage) {
        FeaturedImage = featuredImage;
    }

    public String getUserRating() {
        return UserRating;
    }

    public void setUserRating(String userRating) {
        UserRating = userRating;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

}
