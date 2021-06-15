package com.example.mycontactapp.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {

    private String contact_id, address_id, name_id, personal_id, email, phone, mobile, prefix, first_name,
            mid_name, last_name, suffix, nickname, street, street_2, city,
            province, country, postal, birthday, job_title, department,
            company, website, note = "";

    public Contact(String contact_id, String address_id, String name_id, String personal_id, String email, String phone, String mobile, String prefix, String first_name, String mid_name, String last_name, String suffix, String nickname, String street, String street_2, String city, String province, String country, String postal, String birthday, String job_title, String department, String company, String website, String note) {
        this.contact_id = contact_id;
        this.address_id = address_id;
        this.name_id = name_id;
        this.personal_id = personal_id;
        this.email = email;
        this.phone = phone;
        this.mobile = mobile;
        this.prefix = prefix;
        this.first_name = first_name;
        this.mid_name = mid_name;
        this.last_name = last_name;
        this.suffix = suffix;
        this.nickname = nickname;
        this.street = street;
        this.street_2 = street_2;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postal = postal;
        this.birthday = birthday;
        this.job_title = job_title;
        this.department = department;
        this.company = company;
        this.website = website;
        this.note = note;
    }

    public Contact(String first_name) {

        this.first_name = first_name;
    }

    protected Contact(Parcel in) {
        contact_id = in.readString();
        address_id = in.readString();
        name_id = in.readString();
        personal_id = in.readString();
        email = in.readString();
        phone = in.readString();
        mobile = in.readString();
        prefix = in.readString();
        first_name = in.readString();
        mid_name = in.readString();
        last_name = in.readString();
        suffix = in.readString();
        nickname = in.readString();
        street = in.readString();
        street_2 = in.readString();
        city = in.readString();
        province = in.readString();
        country = in.readString();
        postal = in.readString();
        birthday = in.readString();
        job_title = in.readString();
        department = in.readString();
        company = in.readString();
        website = in.readString();
        note = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contact_id);
        dest.writeString(address_id);
        dest.writeString(name_id);
        dest.writeString(personal_id);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(mobile);
        dest.writeString(prefix);
        dest.writeString(first_name);
        dest.writeString(mid_name);
        dest.writeString(last_name);
        dest.writeString(suffix);
        dest.writeString(nickname);
        dest.writeString(street);
        dest.writeString(street_2);
        dest.writeString(city);
        dest.writeString(province);
        dest.writeString(country);
        dest.writeString(postal);
        dest.writeString(birthday);
        dest.writeString(job_title);
        dest.writeString(department);
        dest.writeString(company);
        dest.writeString(website);
        dest.writeString(note);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getContact_id() {
        return contact_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public String getName_id() {
        return name_id;
    }

    public String getPersonal_id() {
        return personal_id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getMid_name() {
        return mid_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getNickname() {
        return nickname;
    }

    public String getStreet() {
        return street;
    }

    public String getStreet_2() {
        return street_2;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getPostal() {
        return postal;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getJob_title() {
        return job_title;
    }

    public String getDepartment() {
        return department;
    }

    public String getCompany() {
        return company;
    }

    public String getWebsite() {
        return website;
    }

    public String getNote() {
        return note;
    }
}
