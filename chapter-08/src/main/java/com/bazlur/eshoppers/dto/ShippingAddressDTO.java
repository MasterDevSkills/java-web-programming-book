package com.bazlur.eshoppers.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.StringJoiner;

public class ShippingAddressDTO {
	@NotEmpty
	private String address;
	private String address2;

	@NotEmpty
	private String state;

	@NotEmpty
	private String zip;

	@NotEmpty
	private String country;

	@NotEmpty
	@Pattern(regexp = "^(?:\\+88|01)?\\d{11}",
					message = "Must be a valid Bangladeshi phone number")
	private String mobileNumber;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() {

		return new StringJoiner(", ",
						ShippingAddressDTO.class.getSimpleName() + "[", "]")
						.add("address='" + address + "'")
						.add("address2='" + address2 + "'")
						.add("state='" + state + "'")
						.add("zip='" + zip + "'")
						.add("country='" + country + "'")
						.add("mobileNumber='" + mobileNumber + "'")
						.toString();
	}
}
