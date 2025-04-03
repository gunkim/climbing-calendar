package github.gunkim.climbingcalendar.infrastructure.jpa.climbinggym.entity;

import github.gunkim.climbingcalendar.domain.climbinggym.model.ClimbingGym;
import github.gunkim.climbingcalendar.domain.climbinggym.model.id.ClimbingGymId;
import github.gunkim.climbingcalendar.domain.climbinggym.model.vo.GeoLocation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

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

    @Builder(access = AccessLevel.PRIVATE)
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

    public static ClimbingGymEntity from(ClimbingGym climbingGym) {
        return ClimbingGymEntity.builder()
                .id(Optional.ofNullable(climbingGym.id()).map(ClimbingGymId::value).orElse(null))
                .name(climbingGym.name())
                .address(climbingGym.address())
                .latitude(climbingGym.geoLocation().latitude())
                .longitude(climbingGym.geoLocation().longitude())
                .isParkingAvailable(climbingGym.isParkingAvailable())
                .createdAt(climbingGym.createdAt())
                .updatedAt(climbingGym.updatedAt())
                .build();
    }

    public ClimbingGym toDomain() {
        return new ClimbingGym(ClimbingGymId.from(id), name, address, GeoLocation.from(latitude, longitude), isParkingAvailable, createdAt, updatedAt);
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

}
