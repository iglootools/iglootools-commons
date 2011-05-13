package org.iglootools.commons.jpa.gis;


import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * Native SQL Query Helper for GIS Queries.
 * instead of any Criteria API.
 *
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 *
 */
public interface GisQueryBuilder {
    Expression<Double> distanceSpheroidExpression(CriteriaBuilder criteriaBuilder,
                                                  Expression<Geometry> locationExpression,
                                                  Point centroid);

    Predicate withinPredicate(CriteriaBuilder criteriaBuilder,
                              Expression<Geometry> locationExpression,
                              Point centroid,
                              Double numericDistanceInMeters);

}
