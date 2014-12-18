package io.corylee.mapgen.domain;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "T_CORNER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Edge {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@ManyToOne
	private Map map;
	

    @Column(name = "river")
    private Boolean river;

    @ElementCollection
    @CollectionTable(name="T_EDGE_POLY", joinColumns=@JoinColumn(name="edge_id"))
    @Column(name="polygon_id")
    public Set<Long> polygonsConnected;
    
    @ElementCollection
    @CollectionTable(name="T_EDGE_CORNER", joinColumns=@JoinColumn(name="edge_id"))
    @Column(name="corner_id")
    public Set<Long> cornersConnected;
    
    
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Map getMap() {
		return map;
	}


	public void setMap(Map map) {
		this.map = map;
	}


	public Boolean getRiver() {
		return river;
	}


	public void setRiver(Boolean river) {
		this.river = river;
	}
	
	public Set<Long> getPolygonsConnected() {
		return polygonsConnected;
	}


	public void setPolygonsConnected(Set<Long> polygonsConnected) {
		this.polygonsConnected = polygonsConnected;
	}


	public Set<Long> getCornersConnected() {
		return cornersConnected;
	}


	public void setCornersConnected(Set<Long> cornersConnected) {
		this.cornersConnected = cornersConnected;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Edge edge = (Edge) o;

        if (id != null ? !id.equals(edge.id) : edge.id != null) return false;

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
                ", river='" + river + "'" +
                ", cornersConnected='" + cornersConnected + "'" +
                ", polygonsConnected='" + polygonsConnected + "'" +
                '}';
    }
}
