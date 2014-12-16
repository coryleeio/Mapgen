package io.corylee.mapgen.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A MapVersion.
 */
@Entity
@Table(name = "T_MAPVERSION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MapVersion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "max_y", precision=10, scale=2)
    private BigDecimal maxY;

    @Column(name = "max_x", precision=10, scale=2)
    private BigDecimal maxX;

    @Column(name = "version")
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMaxY() {
        return maxY;
    }

    public void setMaxY(BigDecimal maxY) {
        this.maxY = maxY;
    }

    public BigDecimal getMaxX() {
        return maxX;
    }

    public void setMaxX(BigDecimal maxX) {
        this.maxX = maxX;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MapVersion mapVersion = (MapVersion) o;

        if (id != null ? !id.equals(mapVersion.id) : mapVersion.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "MapVersion{" +
                "id=" + id +
                ", maxY='" + maxY + "'" +
                ", maxX='" + maxX + "'" +
                ", version='" + version + "'" +
                '}';
    }
}
