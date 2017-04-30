/* SqlPhotoRepository.java created on Mar 12, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.sql2o;

import static java.lang.Math.min;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static tw.funymph.photowall.utils.MapUtils.getDouble;
import static tw.funymph.photowall.utils.MapUtils.getLong;
import static tw.funymph.photowall.utils.MapUtils.getString;
import static tw.funymph.photowall.utils.StringUtils.join;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import tw.funymph.photowall.core.Location;
import tw.funymph.photowall.core.Photo;
import tw.funymph.photowall.core.repository.PhotoRepository;
import tw.funymph.photowall.core.repository.RepositoryException;

/**
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class SqlPhotoRepository implements PhotoRepository {

	private static final int MaximumRows = 40;

	private Sql2o sql2o;

	public SqlPhotoRepository(Sql2o sql2o) {
		this.sql2o = sql2o;
	}

	@Override
	public Photo[] getPhotos(Long before, Long after, String posterId) {
		List<Photo> result = emptyList();
		List<String> conditions = new ArrayList<>();
		addConditions(conditions, "<= :before", "TIMESTAMP", before);
		addConditions(conditions, ">= :after", "TIMESTAMP", after);
		addConditions(conditions, "= :posterId", "POSTER", posterId);
		String statement = "select * from PHOTO";
		if (conditions.size() > 0) {
			statement += format(" where %s order by TIMESTAMP", join(" and ", conditions.toArray(new String[0])));
		}
		try (Connection connection = sql2o.open()) {
			Query query = connection.createQuery(statement);
			if (before != null) {
				query.addParameter("before", before);
			}
			if (after != null) {
				query.addParameter("after", after);
			}
			if (posterId != null) {
				query.addParameter("posterId", posterId);
			}
			List<Map<String, Object>> rows = query.executeAndFetchTable().asList();
			int size = min(rows.size(), MaximumRows);
			result = rows.subList(0, size)
				.stream()
				.map(this::toPhoto)
				.collect(toList());
		}
		return result.toArray(new Photo[0]);
	}

	private void addConditions(List<String> conditions, String operator, String column, Object value) {
		if (value != null) {
			conditions.add(format("%s %s", column, operator));
		}
	}

	@Override
	public Photo[] getNearbyPhotos(double latitude, double longitude, double distance) {
		Location[] boundingBox = Location.boundingCoordinates(new Location(latitude, longitude), distance);
		String statement = "select * from PHOTO where LATITUDE between :minLat and :maxLat and LONGITUDE between :minLong and :maxLong";
		List<Photo> result = emptyList();
		try (Connection connection = sql2o.open()) {
			Query query = connection.createQuery(statement)
				.addParameter("minLat", boundingBox[0].getLatitude())
				.addParameter("maxLat", boundingBox[1].getLatitude())
				.addParameter("minLong", boundingBox[0].getLongitude())
				.addParameter("maxLong", boundingBox[1].getLongitude());
			List<Map<String, Object>> rows = query.executeAndFetchTable().asList();
			int size = min(rows.size(), MaximumRows);
			result = rows.subList(0, size)
				.stream()
				.map(this::toPhoto)
				.collect(toList());
		}
		return result.toArray(new Photo[0]);
	}

	@Override
	public void save(Photo photo) throws RepositoryException {
		try (Connection connection = sql2o.beginTransaction()) {
			connection.createQuery("insert into PHOTO(ID, POSTER, TIMESTAMP, LATITUDE, LONGITUDE, NAME) values (:id, :poster, :timestamp, :latitude, :longitude, :name)")
				.addParameter("id", photo.getId())
				.addParameter("poster", photo.getPosterId())
				.addParameter("timestamp", photo.getTimestamp())
				.addParameter("latitude", photo.getLocation() != null ? photo.getLocation().getLatitude() : null)
				.addParameter("longitude", photo.getLocation() != null ? photo.getLocation().getLongitude() : null)
				.addParameter("name", photo.getLocation() != null ? photo.getLocation().getName() : null)
				.executeUpdate();
			connection.commit();
		}
		catch (Sql2oException e) {
			throw new RepositoryException(e.getMessage(), e);
		}
	}

	Photo toPhoto(Map<String, Object> map) {
		String photoId = getString(map, "id");
		String posterId = getString(map, "poster");
		long timestamp = getLong(map, "timestamp", -1L);
		Double latitude = getDouble(map, "latitude");
		Double longitude = getDouble(map, "longitude");
		String name = getString(map, "name");
		if (latitude == null || longitude == null) {
			return new Photo(photoId, posterId, timestamp);
		}
		else {
			return new Photo(photoId, posterId, timestamp, new Location(latitude, longitude, name));
		}
	}
}
