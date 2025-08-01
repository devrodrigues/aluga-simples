package devrodrigues.model;

import devrodrigues.model.enums.VehicleStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "vehicle")
public class Vehicle extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
    @Column(name = "vehicle_year")
    private Integer year;
    private String engine;

    public Vehicle() {
    }

    public Vehicle(String brand, String model, Integer year, String engine) {
        this.brand = brand;
        this.model = model;
        this.status = VehicleStatus.AVAILABLE; //veículo é criado já como disponível
        this.year = year;
        this.engine = engine;
    }

    public void changeStatus(VehicleStatus newStatus) {
        if (status.canTransitionTo(newStatus)) {
            this.status = newStatus;
        } else {
            throw new IllegalStateException(
                    String.format("Um veículo com status %s não pode mudar para o status %s", this.status, newStatus)
            );
        }
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public Integer getYear() {
        return year;
    }

    public String getEngine() {
        return engine;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
