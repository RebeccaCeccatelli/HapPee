package frontend.map;

import backend.Address;
import backend.Business;
import backend.BusinessDetails;
import backend.Coordinates;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import database.AddressDAO;
import database.BusinessDAO;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.maps.*;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class Map {

    public static void display() {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        webEngine.setJavaScriptEnabled(true);

        String fileUrl = "file:///C:/Users/Rebecca/IdeaProjects/HapPee/src/frontend/map/LeafletMap.html";
        webEngine.load(fileUrl);

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                placeUser(webEngine);
                placeBusinesses(webEngine);
            }
        });

        Scene scene = new Scene(webView, 800, 600);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void placeUser(WebEngine webEngine) {
        Coordinates userCoordinates = getUserCoordinates();
        if (userCoordinates != null) {
            webEngine.executeScript("setCoordinates("
                    + userCoordinates.getLatitude() + ", "
                    + userCoordinates.getLongitude() + ");");
            webEngine.executeScript("setUserMarker("
                    + userCoordinates.getLatitude() + ", "
                    + userCoordinates.getLongitude() + ", "
                    + "'You are here!');");
        }

    }

    private static void placeBusinesses(WebEngine webEngine) {
        HashMap<Integer, Address> businessAddresses = new AddressDAO().getAllAddresses();
        for (HashMap.Entry<Integer,Address> address : businessAddresses.entrySet()) {

            LatLng businessCoordinates = getCoordinates(address.getValue());
            Business business = new BusinessDAO().getBusinessFromAddressId(address.getKey());

            if (businessCoordinates != null) {
                webEngine.executeScript("setBusinessMarker("
                        + businessCoordinates.lat + ", "
                        + businessCoordinates.lng + ");");
                BusinessDetails businessDetails =  business.getDetails();
                webEngine.executeScript("setBusinessPopup('"
                        + business.getName() + "', '"
                        + businessDetails.getBusinessType() + "', '"
                        + businessDetails.getAccessPrice() + "', '"
                        + businessDetails.getOpeningTime() + "', '"
                        + businessDetails.getClosingTime() + "');");
            }
        }
    }

    private static Coordinates getUserCoordinates() {
        try {
            URL url = new URL("https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyDIPXUh0f0lNnmItHkBv0pzl4Mr7Hu32MQ");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String requestBody = "{}";
            byte[] requestBodyBytes = requestBody.getBytes("UTF-8");
            int contentLength = requestBodyBytes.length;

            connection.setRequestProperty("Content-Length", Integer.toString(contentLength));

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBodyBytes);
            outputStream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();

            JsonObject jsonResponse = JsonParser.parseString(responseBuilder.toString()).getAsJsonObject();
            JsonObject location = jsonResponse.getAsJsonObject("location");
            double latitude = location.get("lat").getAsDouble();
            double longitude = location.get("lng").getAsDouble();
            return new Coordinates(latitude, longitude);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static LatLng getCoordinates(Address address) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDIPXUh0f0lNnmItHkBv0pzl4Mr7Hu32MQ")
                .build();

        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, address.getAddressAsString()).await();
            if (results.length > 0) {
                return results[0].geometry.location;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

