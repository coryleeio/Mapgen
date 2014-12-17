
Development Setup
==========================
This is quick and dirty, and by no means a proper setup for a production environment.

`sudo add-apt-repository ppa:webupd8team/java`


`sudo apt-get update`

`sudo apt-get install oracle-java8-installer oracle-java8-set-default maven -y`

`curl -sL https://deb.nodesource.com/setup | sudo bash -`

`sudo apt-get install nodejs build-essential ruby-full -y`

`sudo npm install -g yo bower generator-jhipster grunt-cli`

`gem update --system`

`gem install compass`

`bower install && sudo npm install && mvn install`


Starting the Server
==========================

Gives you the app on port 8080:

`mvn spring-boot:run`


Gives you automatic reload when you change CSS on port 9000 with connections forwarded to 8080:

`grunt server`

Connect to localhost at the following URL:

`http://localhost:9000`

Design Notes
=========================

MapVersion
  Double         - maxY        - Maximum X Coordinate
  Double         - maxX        - Maximum Y Coordinate
  Integer        - version     - Version number, starting at 1 // Needed for map reverse compatibility, otherwise algorithm changes affect peoples existing maps.

Map
  MapVersion     - mapVersion  - Version for this map.
  User           - user        - Creator of this map

Polygon
  Double         - x           - x bounded by map
  Double         - y           - y bounded by map
  Set<Polygon>   - neighbors   - Set of adjacent Polygons
  Set<Corner>    - corners     - Set of adjacent Corners
  Set<Edge>      - borders     - Set of adjacent Edges
  Boolean        - ocean       - Is ocean.
  Boolean        - water       - Is water.
  Boolean        - coast       - Is land with ocean boarder.
  Elevation      - elevation   - Average of related corners elevation.
  Map            - map

Edge
  Polygon        - d0          - Polygon connected by the edge
  Polygon        - d1          - Polygon connected by the edge
  Corner         - v0          - Corner connected by the edge
  Corner         - v0          - Corner connected by the edge
  Boolean        - river       - Is a river
  Map            - map

Corner
  Double         - x           - x bounded by map
  Double         - y           - y bounded by map
  Set<Polygon>   - touches     - Set of polygons touching the corner.
  Set<Polygon>   - protrudes   - Set of edges touching the corner
  Set<Polygon>   - adjacent    - Set of corners connected to this one
  Boolean        - ocean       - Corner is surrounded by ocean polygons.
  Boolean        - coast       - If corner touches ocean and land polygons.
  Boolean        - water       - If corner surrounding area is water.
  Float          - elevation   - Elevation of this corner.
  Corner         - downhill    - Steepest downhill adjacent corner.
  Moisture       - moisture    - Decreases as distance from fresh water increases.
  Elevation      - elevation   - 
  Map            - map

 Moisture 6(wet) - 1(dry)
 Elevation 4(High) - 1 (low)














