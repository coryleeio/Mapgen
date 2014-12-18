package io.corylee.mapgen.web.rest.dto;

import io.corylee.mapgen.domain.Map;
import io.corylee.mapgen.domain.MapVersion;
import io.corylee.mapgen.service.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class MapDTO {
	private List<String> polygonList = new ArrayList<>();
	private List<String> edgeList = new ArrayList<>();
	private List<String> cornerList = new ArrayList<>();
	private Long id;
	private String maxX = "9999";
	private String maxY = "9999";

	public MapDTO(Map map, MapVersion version, List<Object> polygonList, List<Object> edgeList, List<Object> cornerList)
	{
		id = map.getId();
		if(version != null)
		{
			maxX = version.getMaxX().toString();
			maxY = version.getMaxY().toString();
		}
		this.polygonList = StringUtil.convertObjectListToString(polygonList);
		this.edgeList = StringUtil.convertObjectListToString(edgeList);
		this.cornerList = StringUtil.convertObjectListToString(cornerList);
	}
	
    public List<String> getPolygonList() {
		return polygonList;
	}

	public void setPolygonList(List<String> polygonList) {
		this.polygonList = polygonList;
	}

	public List<String> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(List<String> edgeList) {
		this.edgeList = edgeList;
	}

	public List<String> getCornerList() {
		return cornerList;
	}

	public void setCornerList(List<String> cornerList) {
		this.cornerList = cornerList;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMaxX() {
		return maxX;
	}

	public void setMaxX(String maxX) {
		this.maxX = maxX;
	}

	public String getMaxY() {
		return maxY;
	}

	public void setMaxY(String maxY) {
		this.maxY = maxY;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Map{");
        sb.append("id='").append(id).append('\'');
        sb.append(", maxX='").append(maxX).append('\'');
        sb.append(", maxY='").append(maxY).append('\'');
        sb.append(", polygons='").append(polygonList).append('\'');
        sb.append(", edges='").append(edgeList).append('\'');
        sb.append(", corners='").append(cornerList).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
