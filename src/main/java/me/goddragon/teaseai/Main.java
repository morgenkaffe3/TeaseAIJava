package me.goddragon.teaseai;

public class Main {

    public static double JAVA_VERSION = getJavaVersion();

    public static void main(String[] args) {
        TeaseAI.main(args);
    }

    private static double getJavaVersion() {
        return Double.parseDouble(System.getProperty("java.specification.version"));
    }
}
