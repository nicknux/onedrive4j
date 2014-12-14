# OneDrive4J

## Introduction

This is a Java Client Library for the [OneDrive REST API](http://msdn.microsoft.com/en-us/library/dn659752.aspx).
Developed and tested using Java 1.7.

## Usage

### Basics
```java
// Instantiate OndeDrive client
OneDrive oneDrive = new OneDrive(prop.getProperty("client_id"), prop.getProperty("client_secret"), prop.getProperty("callback_url"));

// The AlbumService provides operations for manipulating albums
AlbumService albumService = oneDrive.getAlbumService();

// The PhotoService provides operations for manipulating photos
PhotoService photoService = oneDrive.getPhotoService();
```

### Authorization
Authorization is done via [OAuth 2.0 Authorization Code Grant Flow](http://msdn.microsoft.com/en-us/library/dn631818.aspx)
Example:
```java
// Obtaining the authorization URL to redirect users to
String authzUrl = oneDrive.authorize(new Scope[] { Scope.SKYDRIVE_UPDATE,  Scope.PHOTOS });
```

If the user grants authorization, they will be redirected to the URL specified in the callback.
Example:
```java
// Obtain code from callback and request for an Access Token
AccessToken token = oneDrive.getAccessToken(code);
```
### Method Invocations
An access token is required to perform the service operations.
Example:
```java
Album[] albums = albumService.getAlbums(token.getAccessToken());
```

## Examples
Examples can be found in ```src/exampkes/java```. It requires that you have a file named ```api_settings.properties``` that you need to place
in ```src/examples/resources```. The contents of the file must be:

```
# OneDrive API Settings
client_id=GET_YOUR_CLIENT_ID
client_secret=GET_YOUR_CLIENT_SECRET
callback_url=http://YOUR_OWN_CALLBACK_URK
```
You need to register your app at [Microsofr account Developer Center](http://go.microsoft.com/fwlink/p/?LinkId=193157) to get your own ClientId and ClientSecret.
