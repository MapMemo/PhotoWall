/* MongoPhotoRepository.java created on Mar 2, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.mongo;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import tw.funymph.photowall.core.Location;
import tw.funymph.photowall.core.Photo;
import tw.funymph.photowall.core.repository.PhotoRepository;
import tw.funymph.photowall.core.repository.RepositoryException;

/**
 * This class provides an implementation of {@link PhotoRepository}
 * with MongoDB technology.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class MongoPhotoRepository implements PhotoRepository {

	private MongoCollection<Document> collection;

	/**
	 * Construct a <code>MongoPhotoRepository</code> instance with
	 * the MongoDB database.
	 * 
	 * @param database the database
	 */
	public MongoPhotoRepository(MongoDatabase database) {
		collection = database.getCollection("photos");
	}

	@Override
	public List<Photo> allPhotos() {
		List<Document> documents = new ArrayList<>();
		return collection.find().into(documents).stream().map(this::fromDocument).collect(toList());
	}

	@Override
	public void save(Photo photo) throws RepositoryException {
		Document document = toDocument(photo);
		try {
			collection.insertOne(document);
		}
		catch (MongoException e) {
			throw new RepositoryException(e.getMessage(), e);
		}
	}

	/**
	 * Convert the photo instance to document.
	 * 
	 * @param photo the photo to convert
	 * @return the document
	 */
	private Document toDocument(Photo photo) {
		Document doc = new Document();
		doc.put("_id", photo.getPhotoId());
		doc.put("posterId", photo.getPhotoId());
		doc.put("timestamp", photo.getTimestamp());
		if (photo.getLocation() != null) {
			Document locDoc = new Document();
			locDoc.put("latitude", photo.getLocation().getLatitude());
			locDoc.put("longitude", photo.getLocation().getLongitude());
			doc.put("location", locDoc);
		}
		return doc;
	}

	/**
	 * Convert the document to photo.
	 * 
	 * @param document the document to convert
	 * @return the photo
	 */
	private Photo fromDocument(Document document) {
		String photoId = document.getString("_id");
		String posterId = document.getString("posterId");
		long timestamp = document.getLong("timestamp").longValue();
		Document locDoc = document.get("location", Document.class);
		if (locDoc != null) {
			double latitude = locDoc.getDouble("latitude");
			double longitude = locDoc.getDouble("longitude");
			Location location = new Location(latitude, longitude);
			return new Photo(photoId, posterId, timestamp, location);
		}
		return new Photo(photoId, posterId, timestamp);
	}
}
