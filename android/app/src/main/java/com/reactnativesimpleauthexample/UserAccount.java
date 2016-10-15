package com.reactnativesimpleauthexample;

import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

public class UserAccount implements Parcelable {
    private String identifier;
    private String fullName;
    private String email;
    private String description;
    private String gender;
    private long day;
    private long month;
    private long year;
    private String pictureURL;
    private String locale;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public WritableMap toMap() {
        WritableMap map = Arguments.createMap();
        map.putString("identifier", identifier);
        map.putString("fullName", fullName);
        map.putString("email", email);
        map.putString("description", description);
        map.putString("gender", gender);
        map.putDouble("day", day);
        map.putDouble("month", month);
        map.putDouble("year", year);
        map.putString("pictureURL", pictureURL);
        map.putString("locale", locale);
        return map;
    }

    public UserAccount() {

    }

    protected UserAccount(Parcel in) {
        identifier = in.readString();
        fullName = in.readString();
        email = in.readString();
        description = in.readString();
        gender = in.readString();
        day = in.readLong();
        month = in.readLong();
        year = in.readLong();
        pictureURL = in.readString();
        locale = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(identifier);
        dest.writeString(fullName);
        dest.writeString(email);
        dest.writeString(description);
        dest.writeString(gender);
        dest.writeLong(day);
        dest.writeLong(month);
        dest.writeLong(year);
        dest.writeString(pictureURL);
        dest.writeString(locale);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserAccount> CREATOR = new Parcelable.Creator<UserAccount>() {
        @Override
        public UserAccount createFromParcel(Parcel in) {
            return new UserAccount(in);
        }

        @Override
        public UserAccount[] newArray(int size) {
            return new UserAccount[size];
        }
    };
}