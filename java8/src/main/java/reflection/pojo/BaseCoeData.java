package reflection.pojo;


import reflection.Jsons;

public class BaseCoeData {

    public String generateLogRaw() {
        return Jsons.format(this);
    }
}
