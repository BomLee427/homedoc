package bom.proj.homedoc.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    private String city;
    private String street;
    private String zipCode;

    public static Address createAddress(String city, String street, String zipCode) {
        return new Address(city, street, zipCode);
    }
}
