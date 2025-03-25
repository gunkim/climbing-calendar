package github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.entity;

import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Entity(name = "climbing_gym")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public ClimbingGymEntity(Long id, String name, String address, double latitude, double longitude, boolean isParkingAvailable, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isParkingAvailable = isParkingAvailable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public static ClimbingGymEntity from(ClimbingGym climbingGym) {
        return new ClimbingGymEntity(climbingGym.id(), climbingGym.name(), climbingGym.address(), climbingGym.latitude(), climbingGym.longitude(),
                climbingGym.isParkingAvailable(), climbingGym.createdAt(), climbingGym.updatedAt());
    }

    public ClimbingGym toDomain() {
        return new ClimbingGym(id, name, address, latitude, longitude, isParkingAvailable, createdAt, updatedAt);
    }
}
