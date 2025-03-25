package github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "climbing_gym")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClimbingGymEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private boolean isParkingAvailable;
    private Instant createdAt;
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClimbingGymEntity that = (ClimbingGymEntity) o;
        return Double.compare(latitude, that.latitude) == 0 && Double.compare(longitude, that.longitude) == 0 && isParkingAvailable == that.isParkingAvailable && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, latitude, longitude, isParkingAvailable, createdAt, updatedAt);
    }
}
