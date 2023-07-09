package backend;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import database.AddressDAO;
import database.BusinessDetailsDAO;
import database.BusinessTableDAO;
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

import com.google.maps.*;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class Map {

    public void display() {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        webEngine.setJavaScriptEnabled(true);

        String fileUrl = "file:///C:/Users/Rebecca/IdeaProjects/HapPee2/src/backend/leafletMap.html";
        webEngine.load(fileUrl);

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                Coordinates userCoordinates = getUserCoordinates();
                if (userCoordinates != null) {
                    webEngine.executeScript("setCoordinates(" + userCoordinates.getLatitude() + ", " + userCoordinates.getLongitude() + ");");
                    webEngine.executeScript("setMarker(" + userCoordinates.getLatitude() + ", " + userCoordinates.getLongitude() + ", 'You are here!');");
                }

                java.util.Map<Integer,Address> businessAddresses = new AddressDAO().getAllAddresses();
                //java.util.Map<Integer, Address> businessAddresses = new HashMap<Integer, Address>();
                //businessAddresses.put(34, new Address("via del Campaccio", "128", "59100", "Prato", "Italia"));
                //businessAddresses.put(37, new Address("Via Libero Grassi", "76", "59100", "Prato", "Italia"));
                for (java.util.Map.Entry<Integer, Address> address : businessAddresses.entrySet()) {
                    LatLng businessCoordinates = getCoordinates(address.getValue());
                    int businessId = new BusinessTableDAO().getBusinessIdFromAddressId(address.getKey());
                    if (businessCoordinates != null) {
                        String script = "setMarker(" + businessCoordinates.lat + ", " + businessCoordinates.lng + ", '" + getBusinessPopupString(businessId) + "');";
                        webEngine.executeScript(script);
                    }
                }
            }
        });

        Scene scene = new Scene(webView, 800, 600);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Coordinates getUserCoordinates() {
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


    private LatLng getCoordinates(Address address) {
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

    private String getBusinessPopupString(int businessId) {
        BusinessTableDAO businessTableManager = new BusinessTableDAO();
        String businessName = businessTableManager.getStringFromDB(businessId, "name");
        int businessDetailsId = new BusinessDetailsDAO().getIdFromBusinessId(businessId);
        String businessType = new BusinessDetailsDAO().getStringFromDB(businessDetailsId, "business_type");
        float accessPrice = new BusinessDetailsDAO().getFLoatFromDB(businessDetailsId, "single_access_price");
        String openingTime = new BusinessDetailsDAO().getTimeFromDB(businessDetailsId, "opening_time").toString();
        String closingTime = new BusinessDetailsDAO().getTimeFromDB(businessDetailsId, "closing_time").toString();

        String string = "Business name: " + businessName + ", category:  " + businessType + ". Pay per use access price: " + accessPrice + ". Opening and closing times: " + openingTime + ", " + closingTime+ ".";
        return string;
    }
}

