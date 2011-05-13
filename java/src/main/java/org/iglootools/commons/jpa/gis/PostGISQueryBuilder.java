package org.iglootools.commons.jpa.gis;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;


public class PostGISQueryBuilder implements GisQueryBuilder {
    private static final Logger logger = LoggerFactory.getLogger(PostGISQueryBuilder.class);

    public static final int WGS84_SRID = 4326;
    private static final String BBOX_FUNCTION = "BOX3D";
    private static final String DISTANCE_SPHERE_FUNCTION = "distance_sphere";
    private static final String DISTANCE_FUNCTION = "distance_sphere";
    private static final String GEOM_FROM_TEXT_FUNCTION = "GeomFromText";
    private static final String OVERLAPS_FUNCTION = "&&";
    private static final String OVERLAPS_FUNCTION_OGC = "overlaps"; // for JPA..
    private static final String POINT = "POINT";
    private static final double RADIUS_OF_EARTH_IN_METERS = 6371008;

    public Predicate withinPredicate(CriteriaBuilder criteriaBuilder,
                                     Expression<Geometry> locationExpression,
                                     Point centroid,
                                     Double numericDistanceInMeters) {

        // Important: make sure all parameters are of type geography, otherwise the unit wont be meters
        // we do not use the official cast syntax because hibernate/JPA2 seems to have issues with that
        // http://www.postgresql.org/docs/9.0/interactive/sql-expressions.html#SQL-SYNTAX-TYPE-CASTS
        return criteriaBuilder.isTrue(
                criteriaBuilder.function(
                "ST_DWithin",
                Boolean.class,
                criteriaBuilder.function("geography", Geometry.class, locationExpression),
                criteriaBuilder.function(
                        "ST_GeographyFromText",
                        Geometry.class,
                        criteriaBuilder.literal(new WKTWriter(2).write(centroid))),
                criteriaBuilder.literal(numericDistanceInMeters)));
    }

    public Expression<Double> distanceSpheroidExpression(CriteriaBuilder criteriaBuilder,
                                                         Expression<Geometry> locationExpression,
                                                         Point centroid) {
        return criteriaBuilder.function(
                "ST_Distance",
                Double.class,
                locationExpression,
                criteriaBuilder.function(
                        GEOM_FROM_TEXT_FUNCTION,
                        Geometry.class,
                        criteriaBuilder.literal(new WKTWriter(2).write(centroid)),
                        criteriaBuilder.literal(WGS84_SRID)),
                criteriaBuilder.literal(true));

    }
}
