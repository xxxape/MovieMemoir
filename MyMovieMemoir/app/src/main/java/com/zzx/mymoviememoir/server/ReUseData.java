package com.zzx.mymoviememoir.server;

import java.util.ArrayList;
import java.util.List;

public class ReUseData {

    private static int maxPerId;
    private static int maxCredId;
    private static int maxCinId;
    private static int maxMemoId;
    private static List<Cinema> cinemas = new ArrayList<>();

    public static int getMaxPerId() {
        maxPerId = maxPerId + 1;
        return maxPerId;
    }

    public static void setMaxPerId(int maxPerId) {
        ReUseData.maxPerId = maxPerId;
    }

    public static int getMaxCredId() {
        maxCredId = maxCredId + 1;
        return maxCredId;
    }

    public static void setMaxCredId(int maxCredId) {
        ReUseData.maxCredId = maxCredId;
    }

    public static int getMaxCinId() {
        maxCinId = maxCinId + 1;
        return maxCinId;
    }

    public static void setMaxCinId(int maxCinId) {
        ReUseData.maxCinId = maxCinId;
    }

    public static int getMaxMemoId() {
        maxMemoId = maxMemoId + 1;
        return maxMemoId;
    }

    public static void setMaxMemoId(int maxMemoId) {
        ReUseData.maxMemoId = maxMemoId;
    }

    public static List<Cinema> getCinemas() {
        return cinemas;
    }

    public static void addCinema(Cinema cinema) {
        ReUseData.cinemas.add(cinema);
    }
}
