package bom.proj.homedoc.domain.hospital;

import bom.proj.homedoc.domain.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital {

    private Hospital(String name, Department department, Address address, String phoneNumber) {
        this.name = name;
        this.department = department;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.hashCode = createHospitalHashcode();
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Long id;

    @Column(unique = true)
    private String hashCode;

    private String name;

    @Enumerated(EnumType.STRING)
    private Department department; //ENT, EYE, INTERNAL, SURGERY, DENTAL

    @Embedded
    private Address address;

    private String phoneNumber;

    @OneToMany(mappedBy = "hospital")
    private List<MemberHospital> memberHospitals;

    public static Hospital createHospital(String name, Department department, String city, String street, String zipCode, String phoneNumber) {
        Address address = Address.createAddress(city, street, zipCode);
        return new Hospital(name, department, address, phoneNumber);
    }

    private String createHospitalHashcode() {
        String hashCode = UUID.randomUUID().toString();
        return hashCode;
    }
}