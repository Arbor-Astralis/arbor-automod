package arbor.astralis.automod;

public final class Branding {
    
    public static final String MAINTAINER = "Haru";
    
    private Branding() {
        
    }
    
    public static String getUnexpectedErrorMessage(String details) {
        return takeRandom(
            "Oops, something went wrong :( Please let " + MAINTAINER + " know what happened.\nError details: " + details
        );
    }

    private static String takeRandom(String ... messages) {
        int randomIndex = (int) (Math.random() * messages.length);
        return messages[randomIndex];
    }

}
