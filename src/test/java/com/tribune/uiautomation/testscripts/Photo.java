/* (C) COPYRIGHT Tribune Interactive
 * Unpublished Work
 * All Rights Reserved
 * Licensed Material - Property of Tribune Interactive
 *
 * This software is the confidential and proprietary information
 * of Tribune Interactive. Unpublished rights are reserved under the copyright
 * laws of the United States.
 *
 * $Id$
 * 
 */

package com.tribune.uiautomation.testscripts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class Photo {

    private static final String AUTH_HEADER = "Authorization";
    private static final String OAUTH2_BEARER = "Bearer";
    private static final String ACCEPT_HEADER = "Accept";
    private static final String ACCEPT_ENCODING_HEADER = "Accept-Encoding";

    private static final String ACCEPT_VALUE = "application/json";
    private static final String ACCEPT_ENCODING_VALUE = "gzip, deflate";

    private static final String PHOTOSERVICES_APP_PATH = "/photos";
    private static final String PHOTOSERVICES_CLONE = "clone";
    private static final String URL_CONTENT_TYPE = "json";

    private static final String NAMESPACE_PARAM = "photo[namespace]";
    private static final String SLUG_PARAM = "photo[slug]";
    private static final String FILE_PARAM = "photo[file]";
    private static final String IMAGE_CONTENT_TYPE = "image/jpeg";

    private static final String JSON_HEIGHT_KEY = "height";
    private static final String JSON_WIDTH_KEY = "width";
    private static final String JSON_SIZE_KEY = "size";
    private static final String JSON_URL_KEY = "url";
    private static final String JSON_NAMESPACE_KEY = "namespace";
    private static final String JSON_SLUG_KEY = "slug";

    private static final String[] ADDITIONAL_SUFFIXES_TO_DELETE = { "-thumbnail", "-altthumbnail" };

    private static final Log log = LogFactory.getLog(Photo.class);

    private static String site;
    private static String auth;

    private String slug;
    private String namespace;
    private String file;
    private String url;
    private int size;
    private int width;
    private int height;
    private boolean persisted = false;

    public Photo() {
	new Photo(null, null, null);
    }

    public Photo(String namespace, String slug) {
	new Photo(namespace, slug, null);
    }

    public Photo(String namespace, String slug, String file) {
	this.slug = slug;
	this.namespace = namespace;
	this.file = file;
    }

    public static void setSite(String site) {
	Photo.site = site;
    }

    public static void setAuth(String auth) {
	Photo.auth = auth;
    }

    public static Photo find(String namespace, String slug) throws JSONException {
	if (namespace == null || slug == null) {
	    return null;
	}
	GetMethod get = new GetMethod(constructResourceUrl(namespace, slug));
	setRequestHeaders(get);
	Photo photo = null;

	try {
	    HttpClient client = new HttpClient();
	    int status = client.executeMethod(get);

	    log.debug("Photo Service find return status: " + get.getStatusLine());

	    if (status == HttpStatus.SC_OK) {
		photo = new Photo();
		processResponse(photo, get.getResponseBodyAsStream());
		photo.setPersisted(true);
	    }
	} catch (HttpException e) {
	    log.fatal("Fatal protocol violation: " + e.getMessage(), e);
	} catch (IOException e) {
	    log.fatal("Fatal transport error: " + e.getMessage(), e);
	} finally {
	    // Release the connection.
	    get.releaseConnection();
	}

	return photo;
    }

    public boolean save() throws JSONException {
	if (persisted) {
	    return update();
	} else {
	    return create();
	}

    }

    public boolean create() throws JSONException {
	checkValidStateForCreate();

	PostMethod post = new PostMethod(constructCreateUrl());

	try {
	    setRequestHeaders(post);
	    setParameters(post);

	    HttpClient client = new HttpClient();
	    int status = client.executeMethod(post);

	    log.debug("Photo Service create return status: " + post.getStatusLine());
	    if (status == HttpStatus.SC_CREATED) {
		processResponse(this, post.getResponseBodyAsStream());
		persisted = true;
		return true;
	    }
	} catch (HttpException e) {
	    log.fatal("Fatal protocol violation: " + e.getMessage(), e);
	} catch (IOException e) {
	    log.fatal("Fatal transport error: " + e.getMessage(), e);
	} finally {
	    // Release the connection.
	    post.releaseConnection();
	}

	return false;
    }

    public Photo clonePhoto(String newNamespace, String newSlug) throws JSONException {
	checkValidStateForDelete();
	Photo photo = null;

	PostMethod post = new PostMethod(constructCloneUrl(namespace, slug));

	try {
	    setRequestHeaders(post);
	    setParameters(post, newNamespace, newSlug);

	    HttpClient client = new HttpClient();
	    int status = client.executeMethod(post);

	    log.debug("Photo Service create return status: " + post.getStatusLine());
	    if (status == HttpStatus.SC_OK) {
		photo = new Photo();
		processResponse(photo, post.getResponseBodyAsStream());
		photo.setPersisted(true);
	    }
	} catch (HttpException e) {
	    log.fatal("Fatal protocol violation: " + e.getMessage(), e);
	} catch (IOException e) {
	    log.fatal("Fatal transport error: " + e.getMessage(), e);
	} finally {
	    // Release the connection.
	    post.releaseConnection();
	}

	return photo;
    }

    public boolean update() throws JSONException {
	checkValidStateForCreate();

	PutMethod put = new PutMethod(constructResourceUrl(namespace, slug));

	try {
	    setRequestHeaders(put);
	    setParameters(put);

	    HttpClient client = new HttpClient();
	    int status = client.executeMethod(put);

	    log.debug("Photo Service update return status: " + put.getStatusLine());
	    if (status == HttpStatus.SC_OK) {
		processResponse(this, put.getResponseBodyAsStream());
		return true;
	    }
	} catch (HttpException e) {
	    log.fatal("Fatal protocol violation: " + e.getMessage(), e);
	} catch (IOException e) {
	    log.fatal("Fatal transport error: " + e.getMessage(), e);
	} finally {
	    // Release the connection.
	    put.releaseConnection();
	}

	return false;
    }

    public boolean delete() {
	return delete(true);
    }

    public boolean delete(boolean deleteThumbnails) {
	boolean returnAnythingDeleted = false;
	returnAnythingDeleted = delete(namespace, slug);

	if (deleteThumbnails) {
	    for (int i = 0; i < ADDITIONAL_SUFFIXES_TO_DELETE.length; i++) {
		boolean deleted = delete(namespace, slug + ADDITIONAL_SUFFIXES_TO_DELETE[i]);
		if (!returnAnythingDeleted && deleted) {
		    returnAnythingDeleted = deleted;
		}
	    }
	}

	return returnAnythingDeleted;
    }

    private boolean delete(String namespaceParam, String slugParam) {
	checkValidStateForDelete();

	DeleteMethod delete = new DeleteMethod(constructResourceUrl(namespaceParam, slugParam));

	try {
	    setRequestHeaders(delete);

	    HttpClient client = new HttpClient();
	    int status = client.executeMethod(delete);

	    log.debug("Photo Service delete return status: " + delete.getStatusLine());
	    if (status == HttpStatus.SC_OK) {
		return true;
	    }
	} catch (HttpException e) {
	    log.fatal("Fatal protocol violation: " + e.getMessage(), e);
	} catch (IOException e) {
	    log.fatal("Fatal transport error: " + e.getMessage(), e);
	} finally {
	    // Release the connection.
	    delete.releaseConnection();
	}

	return false;
    }

    public int getSize() {
	return size;
    }

    public int getHeight() {
	return height;
    }

    public int getWidth() {
	return width;
    }

    public String getUrl() {
	return url;
    }

    public void setFile(String file) {
	this.file = file;
    }

    public void setSlug(String slug) {
	this.slug = slug;
    }

    public void setNamespace(String namespace) {
	this.namespace = namespace;
    }

    private void checkValidStateForCreate() throws IllegalStateException {
	if (file == null || namespace == null || slug == null || site == null || auth == null) {
	    throw new IllegalStateException("An input file, slug, namespace, site, and auth must be set in order to update/create a photo");
	}
    }

    private void checkValidStateForDelete() throws IllegalStateException {
	if (namespace == null || slug == null || site == null || auth == null) {
	    throw new IllegalStateException("A slug, namespace, site, and auth must be set in order to delete a photo");
	}
    }

    private void setPersisted(boolean persisted) {
	this.persisted = persisted;
    }

    private void setSize(int size) {
	this.size = size;
    }

    private void setHeight(int height) {
	this.height = height;
    }

    private void setWidth(int width) {
	this.width = width;
    }

    private void setUrl(String url) {
	this.url = url;
    }

    private static String constructCreateUrl() {
	return site + PHOTOSERVICES_APP_PATH + '.' + URL_CONTENT_TYPE;
    }

    private static String constructCloneUrl(String namespace, String slug) {
	return site + PHOTOSERVICES_APP_PATH + '/' + PHOTOSERVICES_CLONE + '/' + namespace + '/' + slug + '.' + URL_CONTENT_TYPE;
    }

    private static String constructResourceUrl(String namespace, String slug) {
	return site + PHOTOSERVICES_APP_PATH + '/' + namespace + '/' + slug + '.' + URL_CONTENT_TYPE;
    }

    private static void processResponse(Photo photo, InputStream stream) throws IOException, JSONException {
	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	byte[] byteArray = new byte[1024];
	int count = 0;
	while ((count = stream.read(byteArray, 0, byteArray.length)) > 0) {
	    bytes.write(byteArray, 0, count);
	}

	String response = new String(bytes.toByteArray());
	log.debug("Photo Service response: " + response);

	JSONObject json = new JSONObject(response);
	log.debug("Photo Service response converted to JSON: " + json);

	setPhotoAttributesFromJSON(photo, json);
    }

    private static void setPhotoAttributesFromJSON(Photo photo, JSONObject json) throws JSONException {
	photo.setHeight(json.getInt(JSON_HEIGHT_KEY));
	photo.setWidth(json.getInt(JSON_WIDTH_KEY));
	photo.setSize(json.getInt(JSON_SIZE_KEY));
	photo.setUrl(json.getString(JSON_URL_KEY));
	photo.setNamespace(json.getString(JSON_NAMESPACE_KEY));
	photo.setSlug(json.getString(JSON_SLUG_KEY));
    }

    private void setParameters(EntityEnclosingMethod method) throws FileNotFoundException {
	setParameters(method, null, null);
    }

    private void setParameters(EntityEnclosingMethod method, String newNamespace, String newSlug) throws FileNotFoundException {
	StringPart namespacePart = newNamespace == null ? new StringPart(NAMESPACE_PARAM, namespace) : new StringPart(NAMESPACE_PARAM, newNamespace);
	StringPart slugPart = newSlug == null ? new StringPart(SLUG_PARAM, slug) : new StringPart(SLUG_PARAM, newSlug);
	// next two lines annoying work around for this bug:
	// https://github.com/rack/rack/issues/186
	namespacePart.setContentType(null);
	slugPart.setContentType(null);

	if (file != null) {
	    File f = new File(file);
	    Part[] parts = { namespacePart, slugPart, new FilePart(FILE_PARAM, f, IMAGE_CONTENT_TYPE, FilePart.DEFAULT_CHARSET) };
	    method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
	} else {
	    Part[] parts = { namespacePart, slugPart };
	    method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
	}
    }

    private static void setRequestHeaders(HttpMethod method) {
	method.addRequestHeader(AUTH_HEADER, OAUTH2_BEARER + ' ' + auth);
	method.addRequestHeader(ACCEPT_HEADER, ACCEPT_VALUE);
	method.addRequestHeader(ACCEPT_ENCODING_HEADER, ACCEPT_ENCODING_VALUE);
    }

    @Override
    public String toString() {
	return JSON_HEIGHT_KEY + ": " + this.height + ' ' + JSON_WIDTH_KEY + ": " + this.width + ' ' + JSON_SIZE_KEY + ": " + this.size + ' ' + JSON_URL_KEY
	        + ": " + this.url + ' ' + JSON_NAMESPACE_KEY + ": " + this.namespace + ' ' + JSON_SLUG_KEY + ": " + this.slug;
    }

    public static void main(String args[]) throws JSONException {
	String filename = "D:/Xebia_Projects/Projects/git/Tribune/src/test/resources/images/Rest_testing.png";
	// String filename2 =
	// "/Users/dbutler/code/ruby/photo_services_azure/test_script/testimages/foo4.jpeg";
	// Photo.setAuth("5xih6pup6e8isq53muu6cfipwhcqmpeop8c");
	Photo.setAuth("Bearer gvg_aq7asjhg75g912ifyrc0ljo4s_czrjjvz3q2dx9x46vrvw8x7nu");
	// Photo.setSite("http://lalvsphotosvc01.tila.trb");
	Photo.setSite("http://fclsphotosvc01.extranet.tribune.com");

	// Create a new photo
	Photo p = new Photo("testdab", "javatest100", filename);
	p.save();
	p = new Photo("testdab", "javatest100-thumbnail", filename);
	p.save();
	p = new Photo("testdab", "javatest100-altthumbnail", filename);
	p.save();

	// Look up that photo and change the image
	p = Photo.find("testdab", "javatest100");
	System.out.println(p);
	// p.setFile(filename2);
	p.save();
	System.out.println(p);

	// Look up that photo and clone it
	p = Photo.find("testdab", "javatest100");
	System.out.println(p);
	Photo p2 = p.clonePhoto("testdab", "javatest100clone");
	System.out.println(p2);

	// Look up that photo image, with no file attached
	// This more closely models real world usage for deletes
	p = Photo.find("testdab", "javatest100");
	// Now delete it
	p.delete();
	p = Photo.find("testdab", "javatest100clone");
	p.delete();

    }

}