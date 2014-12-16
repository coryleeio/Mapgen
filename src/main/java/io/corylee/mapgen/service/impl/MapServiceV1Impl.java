package io.corylee.mapgen.service.impl;

import io.corylee.mapgen.service.MapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MapServiceV1Impl implements MapService {
	
    private final Logger log = LoggerFactory.getLogger(MapServiceV1Impl.class);
}
