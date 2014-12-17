package io.corylee.mapgen.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "T_CORNER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Corner implements Serializable {

	private static final long serialVersionUID = -8310345595092676585L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "x", precision=10, scale=2)
    private BigDecimal x;

    @Column(name = "y", precision=10, scale=2)
    private BigDecimal y;

    @Column(name = "ocean")
    private Boolean ocean;

    @Column(name = "water")
    private Boolean water;

    @Column(name = "coast")
    private Boolean coast;

    @Column(name = "elevation")
    private Integer elevation;

    @Column(name = "moisture")
    private Integer moisture;
    
    @ManyToOne
    private Map map;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getX() {
		return x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}

	public Boolean getOcean() {
		return ocean;
	}

	public void setOcean(Boolean ocean) {
		this.ocean = ocean;
	}

	public Boolean getWater() {
		return water;
	}

	public void setWater(Boolean water) {
		this.water = water;
	}

	public Boolean getCoast() {
		return coast;
	}

	public void setCoast(Boolean coast) {
		this.coast = coast;
	}

	public Integer getElevation() {
		return elevation;
	}

	public void setElevation(Integer elevation) {
		this.elevation = elevation;
	}
	
    public Integer getMoisture() {
		return moisture;
	}

	public void setMoisture(Integer moisture) {
		this.moisture = moisture;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Corner corner = (Corner) o;

        if (id != null ? !id.equals(corner.id) : corner.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "id=" + id +
                ", x='" + x + "'" +
                ", y='" + y + "'" +
                ", ocean='" + ocean + "'" +
                ", water='" + water + "'" +
                ", coast='" + coast + "'" +
                ", elevation='" + elevation + "'" +
                ", moisture='" + moisture + "'" +
                '}';
    }
}